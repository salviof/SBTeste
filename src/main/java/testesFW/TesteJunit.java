/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import testesFW.geradorDeCodigo.util.UtilSBDevelGeradorCodigoModel;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef.GeradorReferenciaCampos;
import java.util.List;
import org.junit.Before;

/**
 *
 * @author sfurbino
 */
public abstract class TesteJunit extends org.junit.Assert {

    protected abstract void configAmbienteDesevolvimento();

    private static boolean inicializou = false;

    public void preenchimentoAleatorio(ItfBeanSimples o) {
        o.getCamposInstanciados().forEach(cp -> cp.preenchimentoAleatorio());
    }

    @Before
    public void initPadrao() {
        try {
            if (!inicializou) {
                configAmbienteDesevolvimento();
                System.out.println("INIT PADRAO");

                inicializou = true;
            }
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

    protected void configApenasLog() {
        throw new UnsupportedOperationException("O config de logs Ainda não foi implementado");
    }

    protected void lancarErroJUnit(Throwable erro) {
        // TODO MELHORAR NULL POINT EXCEPTION
        SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "ERRO EM TESTE: \n" + erro.getMessage(), erro);
        throw new UnknownError("Aconteceu um erro na execução do teste" + erro.getMessage());

    }

    public void gerarCodigoModelProjeto() {
        List<EstruturaDeEntidade> objetos = MapaObjetosProjetoAtual.getListaTodosEstruturaObjeto();
        objetos.forEach(est -> {
            Class classe = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(est.getNomeEntidade());
            if (!classe.isAssignableFrom(ItfAcaoDoSistema.class)) {
                if (!classe.getSimpleName().startsWith("Acao") && !classe.getSimpleName().startsWith("estrutura")) {
                    GeradorReferenciaCampos ref = new GeradorReferenciaCampos(classe);
                    ref.salvarEmDiretorioPadraoSubstituindoAnterior();
                }

            }

            try {
                if (est.isTemCampoValidadoresLogicos()) {
                    criarAnotacaoValidacao(est);
                }
                if (est.isTemCampoValorLogico()) {
                    criarAnotacaoCalculo(est);
                }
                if (est.isTemCampoListaDinamica()) {
                    criarAnotacaoLista(est);
                }

            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, t.getMessage(), t);
            }

        });

    }

    public void criarAnotacaoValidacao(EstruturaDeEntidade estEstrutura) {
        UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValidadoresApi(estEstrutura);
        estEstrutura.getCamposComValidadorLogico().forEach(UtilSBDevelGeradorCodigoModel::homologarClassesDeValidacao);

    }

    public void criarAnotacaoCalculo(EstruturaDeEntidade calculo) {
        UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValorLogicaApi(calculo);
        calculo.getCamposComValorLogico().forEach(UtilSBDevelGeradorCodigoModel::homologarClassesDeValor);
    }

    public void criarAnotacaoLista(EstruturaDeEntidade lista) {
        UtilSBDevelGeradorCodigoModel.gerarCodigoCampoListasApi(lista);
        lista.getCamposComListaDinamica().forEach(UtilSBDevelGeradorCodigoModel::gerarCodigoCampoListaDinamica);
    }

}
