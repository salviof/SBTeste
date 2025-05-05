/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDiretorio;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringSlugs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringsMaiuculoMinusculo;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import cucumber.api.CucumberOptions;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.RuntimeGlue;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.UndefinedStepsTracker;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.StepContainer;
import gherkin.formatter.model.Step;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class UtilSBCucumber {

    public static EtapaCucumber getEtapaInstanciadaBySepFile(List<EtapaCucumber> etapasImplementadas, Step pStep) {
        for (EtapaCucumber etapa : etapasImplementadas) {
            System.out.println(etapa.getAnotacaoIndicada());
            System.out.println(etapa.getDescritivo());
            System.out.println(etapa.getNOME_SLUG_VARIAVEL());
            System.out.println(etapa.getTagFuncionalidade());
            System.out.println(pStep.getName());
            System.out.println(gerarNomeVariavelDaEtapaByStep(pStep));
            System.out.println(etapa.getNOME_SLUG_VARIAVEL());
            System.out.println(gerarNomeVariavelDaEtapaByStep(pStep));
            String nomeEtapa = etapa.getNOME_SLUG_VARIAVEL();

            String nomeIdeal = gerarNomeVariavelDaEtapaByStep(pStep);//.replace(etapa.getAnotacaoIndicada().getSimpleName(), "E");
            String nomeIdealAlternativo = UtilSBCoreStringFiltros.substituirPrimeiro(gerarNomeVariavelDaEtapaByStep(pStep), "E", etapa.getAnotacaoIndicada().getSimpleName().toUpperCase());

            //gerarNomeVariavelDaEtapaByStep(pStep).replace("E", etapa.getAnotacaoIndicada().getSimpleName());
            if (nomeEtapa.equals(nomeIdeal)
                    || nomeEtapa.equals(nomeIdealAlternativo)) {
                return etapa;
            }
        }
        return null;
    }

    public static void gerarVariaveisEstaticasDasEtapas(List<EtapaCucumber> etapas, String pTagFuncionalidade) {
        try {
            GeradorVariaveisEstaticasEtapasCucumber gerador = new GeradorVariaveisEstaticasEtapasCucumber(etapas, pTagFuncionalidade);
            gerador.salvarEmDiretorioPadraoSubstituindoAnterior();
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha criando Implementação " + pTagFuncionalidade, t);
        }

    }

    public static void gerarImplementacaoPadrao(List<EtapaCucumber> etapas, String pTagFuncionalidade) {
        GeradorVariaveisEstaticasEtapasCucumber gerador = new GeradorVariaveisEstaticasEtapasCucumber(etapas, pTagFuncionalidade);
        gerador.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static String getTipoEtapaByMetodo(Method pMetodo) {

        String tipoEtapa = getAnotacaoTipoEtapaDoMetodo(pMetodo).getSimpleName();
        return tipoEtapa;
    }

    public static Class getAnotacaoTipoEtapaDoMetodo(Method pMetodo) {
        Annotation anotacao = pMetodo.getDeclaredAnnotations()[0];
        return anotacao.annotationType();
    }

    public static String gerarNomeVariavelDaEtapaByStep(Step pEtapa) {
        String textoEtapa = pEtapa.getName();
        String tipoEtapa = pEtapa.getKeyword();
        return gerarNomeVariavelDaEtapa(tipoEtapa, textoEtapa);
    }

    public static String gerarNomeVariavelDaEtapa(String pTipoEtapa, String pTextoEtapa) {
        String nomeCompleto = pTipoEtapa + " " + pTextoEtapa;
        String nomeVariavel = UtilSBCoreStringSlugs.gerarSlugCaixaAlta(nomeCompleto);
        nomeVariavel = nomeVariavel.replace("__", "_");
        return nomeVariavel;
    }

    public static String gerarNomeClasseImplementacaoDaEtapa(String pTipoEtapa, String pTextoEtapa) {
        String nomeCompleto = pTipoEtapa + " " + pTextoEtapa;
        nomeCompleto = nomeCompleto.replace("  ", " ");
        String nomeClasse = UtilSBCoreStringFiltros.gerarUrlAmigavel(nomeCompleto);
        nomeClasse = UtilSBCoreStringsMaiuculoMinusculo.getPrimeiraLetraMaiusculo(nomeClasse);
        return nomeClasse;
    }

    public static void gerarClasseImplementacaoEtapa(EtapaCucumber pEtapa, String pTagFuncionalidade) {

        GeradorEtapaImplementacao gerador = new GeradorEtapaImplementacao(pTagFuncionalidade, pEtapa);

        gerador.salvarEmDiretorioPadraCASO_NAO_EXISTA();

    }

    public static String gerarCaminhoDiretorioClasseEtapa(EtapaCucumber etapa) {
        String nomePAcote = "org.coletivoJava.fw.projetos." + SBCore.getGrupoProjeto() + ".implementacao.cucumber." + etapa.getTagFuncionalidade().toLowerCase() + ".etapas";
        String direPacore = nomePAcote.replace(".", "/");
        String diretorioDesenvolvimento = SBCore.getCaminhoDesenvolvimento() + "/src/test/java/";
        String caminhoDiretorioClasse = diretorioDesenvolvimento + direPacore;
        return caminhoDiretorioClasse;

    }

    public static String getTagFincionalidade(Annotation[] pAnotacoes) {
        for (Annotation anotacao : pAnotacoes) {
            if (anotacao instanceof CucumberOptions) {
                CucumberOptions anotacaoCucumber = (CucumberOptions) anotacao;
                //Obtendo a Tag de Funcnalidade suporta penas uma tag

                if (anotacaoCucumber.tags().length > 1) {
                    throw new UnsupportedOperationException(CucumberSBTestes.class.getSimpleName() + " Só é compativel com uma tag de funcionalidade,foi encontrado " + anotacaoCucumber.tags());
                }
                return anotacaoCucumber.tags()[0].replace("@", "");

            }
        }
        return null;
    }

    public static Class getAnotacaoByStep(Step pStep) {

        switch (pStep.getKeyword()) {
            case "Dado":
            case "Dado ":
                return Dado.class;
            case "Quando":
            case "Quando ":
                return Quando.class;
            case "E":
            case "E ":
                return E.class;
            case "Entao":
            case "Entao ":
            case "Então":
            case "Então ":
                return Entao.class;

        }
        return null;
    }

    public static List<Step> getReflexaoEtapasNaoInjetadas(RuntimeGlue pRuntimeGlue) {

        Field campoetapasNaoLocalizadas;
        try {
            campoetapasNaoLocalizadas = RuntimeGlue.class.getDeclaredField("tracker");
            campoetapasNaoLocalizadas.setAccessible(true);
            UndefinedStepsTracker etapasNaoInjetadasTracker = (UndefinedStepsTracker) campoetapasNaoLocalizadas.get(pRuntimeGlue);
            Field campoEtapasnaoInjetasa = etapasNaoInjetadasTracker.getClass().getDeclaredField("undefinedSteps");
            campoEtapasnaoInjetasa.setAccessible(true);
            return (List) campoEtapasnaoInjetasa.get(etapasNaoInjetadasTracker);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static RuntimeGlue getReflexaoAnalizadorGherkin(CucumberSBTestes pCucumber) {

        Field runtimeCampo;
        try {
            runtimeCampo = Cucumber.class.getDeclaredField("runtime");
            runtimeCampo.setAccessible(true);
            cucumber.runtime.Runtime cucumberEmExecucao = (cucumber.runtime.Runtime) runtimeCampo.get(pCucumber);
            System.out.println(cucumberEmExecucao);
            return (RuntimeGlue) cucumberEmExecucao.getGlue();
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static List<EtapaCucumber> getReflexaotEtapasImplementasNoPacote(RuntimeGlue pRuntimeGlue, Annotation[] pAnotacoesFluxo) {
        List<EtapaCucumber> etapasImplementadas = new ArrayList<>();
        Field campodefinicoesDeEtapaPorPadrao;
        try {
            campodefinicoesDeEtapaPorPadrao = RuntimeGlue.class.getDeclaredField("stepDefinitionsByPattern");
            campodefinicoesDeEtapaPorPadrao.setAccessible(true);

            Map<String, StepDefinition> mapaetapasEncontradas = (Map<String, StepDefinition>) campodefinicoesDeEtapaPorPadrao.get(pRuntimeGlue);
            mapaetapasEncontradas.values().stream().forEach(etapa -> {
                try {
                    String texto = etapa.getPattern();
                    Field campoMetodo = etapa.getClass().getDeclaredField("method");
                    campoMetodo.setAccessible(true);
                    Method metodo = (Method) campoMetodo.get(etapa);
                    String tagFuncionalidade = UtilSBCucumber.getTagFincionalidade(pAnotacoesFluxo);
                    EtapaCucumber etapaCucumber = new EtapaCucumber(metodo, texto, tagFuncionalidade);
                    if (!etapasImplementadas.contains(etapaCucumber)) {
                        etapasImplementadas.add(etapaCucumber);
                    }

                } catch (Throwable t) {

                }
                // etapasEncontradas.add(etapa.)
            });

            return etapasImplementadas;
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<Step> getReflexaoEtapasArquivo(CucumberSBTestes pCucucumber) {

        try {
            if (!pCucucumber.getChildren().isEmpty()) {

                FeatureRunner execucaoFuncionalidade = pCucucumber.getChildren().get(0);
                Field campoFuncionalidade;
                campoFuncionalidade = execucaoFuncionalidade.getClass().getDeclaredField("cucumberFeature");
                campoFuncionalidade.setAccessible(true);
                CucumberFeature cucumberFeature = (CucumberFeature) campoFuncionalidade.get(execucaoFuncionalidade);
                Field campoStepContainer = CucumberFeature.class.getDeclaredField("currentStepContainer");
                campoStepContainer.setAccessible(true);

                StepContainer containerDeEtapas = (StepContainer) campoStepContainer.get(cucumberFeature);
                return containerDeEtapas.getSteps();
            }

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(UtilSBCucumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
