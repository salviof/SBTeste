/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreOrdenacaoAlfabeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexao;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeGlue;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.FeatureRunner;
import gherkin.formatter.model.Step;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.junit.rules.TestRule;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import testesFW.ItfTestesSBCore;

/**
 *
 * @author sfurbino
 */
public class CucumberSBTestes extends Cucumber {

    private final RuntimeGlue analizadorGherkin;
    private final List<EtapaCucumber> etapasNaoEncontradas = new ArrayList();

    private final List<Step> stepsNaoInjetadas;
    private final List<Step> stepsArquivoFeature;
    private final List<EtapaCucumber> etapasImplementadasNoPacote = new ArrayList();

    public CucumberSBTestes(Class classe) throws InitializationError, IOException {
        super(classe);
        //  getChildren().

        try {
            TesteIntegracaoFuncionalidadeCucumber.CLASSE_FLUXO = classe;
            analizadorGherkin = UtilSBCucumber.getReflexaoAnalizadorGherkin(this);
            stepsNaoInjetadas = UtilSBCucumber.getReflexaoEtapasNaoInjetadas(analizadorGherkin);
            etapasImplementadasNoPacote.addAll(UtilSBCucumber.getReflexaotEtapasImplementasNoPacote(analizadorGherkin, getRunnerAnnotations()));
            stepsArquivoFeature = UtilSBCucumber.getReflexaoEtapasArquivo(this);

            // etapasNaoInjetadas.
        } catch (Throwable t) {
            throw new UnsupportedOperationException("Falha inicializando cucumber" + t.getMessage());
        }

    }

    public void numerarClasses() {
        int i = 1;
        for (Step stepFile : stepsArquivoFeature) {

            EtapaCucumber etapa = UtilSBCucumber.getEtapaInstanciadaBySepFile(etapasImplementadasNoPacote, stepFile);
            if (etapa != null) {

                String classe = etapa.getNomeClasseImplementada();

                // Obter o nome da classe (com pacote)
                String parteAnalizada = classe.substring(0, 2);
                boolean umaNomvaOrdenacao = !parteAnalizada.contains("_");

                String valorAtual = UtilSBCoreStringFiltros.getApenasLetras(parteAnalizada);

                String letraOrdemCorreta = UtilSBCoreOrdenacaoAlfabeto.numeroParaLetras(i);
                if (!valorAtual.equals(letraOrdemCorreta)) {
                    String parteNova = "";
                    if (umaNomvaOrdenacao) {
                        parteNova = letraOrdemCorreta + "_" + classe.substring(0, 2);
                    } else {
                        parteNova = parteAnalizada.replace(valorAtual, letraOrdemCorreta);
                    }
                    String novoNomeClasse = parteNova + classe.substring(2, classe.length());
                    String caminho = UtilSBCucumber.gerarCaminhoDiretorioClasseEtapa(etapa);
                    String arquivoAntigo = caminho + "/" + classe + ".java";
                    String arquivoNovo = caminho + "/" + novoNomeClasse + ".java";

                    UtilSBCoreArquivos.renomearArquivo(arquivoAntigo, arquivoNovo);
                    UtilSBCoreArquivoTexto.substituirTextoNoArquivo(arquivoNovo, classe, novoNomeClasse);

                }
            }
            i++;
        }
    }

    @Override
    public void run(RunNotifier notifier) {

        try {
            ItfTestesSBCore teste = (ItfTestesSBCore) getTestClass().getJavaClass().newInstance();
            teste.configContextoExecucao();
            numerarClasses();
        } catch (InstantiationException ex) {
            Logger.getLogger(CucumberSBTestes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CucumberSBTestes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            super.run(notifier);
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.PARA_TUDO, "Falha catastrófica (frase em homenagem ao Delphi), "
                    + "que significa que o sistema precisa parar, um erro precisa ser corrigido", t);
        }
        List<EtapaCucumber> todasEtapas = new ArrayList();

        if (!stepsNaoInjetadas.isEmpty()) {

            String tagFuncionalidade = UtilSBCucumber.getTagFincionalidade(getRunnerAnnotations());
            final String tagFuncionalidadeSemArroba = tagFuncionalidade.replace("@", "");
            Map<String, Step> mapaEtapasDoArquivoFuncionalidadePorDescricao = new HashMap<>();
            stepsArquivoFeature.stream().forEach(etapa -> {
                EtapaCucumber novaEtapa = new EtapaCucumber(etapa, tagFuncionalidadeSemArroba);
                if (!todasEtapas.contains(novaEtapa)) {
                    todasEtapas.add(novaEtapa);
                    mapaEtapasDoArquivoFuncionalidadePorDescricao.put(etapa.getName(), etapa);
                }
            });

            System.out.println("Foram encontradas etapas sem mapeamento entre o metodo java e o arquivo Gherkin .feature");

            for (Step etapa : stepsNaoInjetadas) {
                try {
                    EtapaCucumber etapaCucumber = new EtapaCucumber(etapa,
                            tagFuncionalidadeSemArroba);
                    if (!etapasNaoEncontradas.contains(etapaCucumber)) {
                        etapasNaoEncontradas.add(etapaCucumber);
                    }
                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha processando etapas não injetada." + t.getMessage(), t);
                }
            }

            System.out.println("O sistema irá criar a classe com Strings estáticas que definem as funcionalidades" + tagFuncionalidade);
            SBCore.getServicoComunicacao().gerarComunicacaoSistema_UsuarioLogado(FabTipoComunicacao.NOTIFICAR, tagFuncionalidade);
            UtilSBCucumber.gerarVariaveisEstaticasDasEtapas(todasEtapas, tagFuncionalidadeSemArroba);
            etapasNaoEncontradas.stream().forEach(etapa -> {
                if (SBCore.getServicoComunicacao().aguardarRespostaComunicacao(
                        SBCore.getServicoComunicacao().getFabricaCanalPadrao().getRegistro(),
                        SBCore.getServicoComunicacao().gerarComunicacaoSistema_UsuarioLogado(FabTipoComunicacao.PERGUNTAR_SIM_OU_NAO,
                                "A imlementação da etapa:" + etapa.getDescritivo() + " \n não foi encontrada, isso pode acontecer por uma mudança de nome, ou criação de nova etapa, você deve criar uma nova classe apenas se tiver adicionado uma nova etapa, deseja criar uma nova classe para implementar a etapa?"),
                        0, FabTipoRespostaComunicacao.SIM) == FabTipoRespostaComunicacao.SIM) {
                    UtilSBCucumber.gerarClasseImplementacaoEtapa(etapa, tagFuncionalidadeSemArroba);
                }

            });

        }
    }

    @Override
    protected List<TestRule> classRules() {
        return super.classRules(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void collectInitializationErrors(List<Throwable> errors) {
        super.collectInitializationErrors(errors); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader, RuntimeOptions runtimeOptions) throws InitializationError, IOException {
        return super.createRuntime(resourceLoader, classLoader, runtimeOptions); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        super.runChild(child, notifier); //To change body of generated methods, choose Tools | Templates.
    }

}
