/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import testesFW.geradorDeCodigo.util.UtilSBDevelGeradorCodigoModel;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef.GeradorReferenciaCampos;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Before;

/**
 *
 * @author sfurbino
 */
public abstract class TesteJunit extends org.junit.Assert implements ItfTestesSBCore {

    protected abstract void configAmbienteDesevolvimento();

    private static boolean inicializou = false;

    @Override
    public void configContextoExecucao() {
        configAmbienteDesevolvimento();
    }

    public void preenchimentoAleatorio(ComoEntidadeSimples o) {
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

        if (CarameloCode.isProjetoModuloERP()) {
            throw new UnsupportedOperationException("Utilize " + TesteJunitJPAModuloERP.class.getSimpleName() + ".gerarCodigoModelERP() ");
        }
        boolean contextoERP = CarameloCode.isProjetoModuloERP();
        if (!(CarameloCode.getTipoProjeto().equals(FabTipoProjeto.MODEL) || CarameloCode.getTipoProjeto().equals(FabTipoProjeto.MODEL_E_CONTROLLER))) {
            throw new UnsupportedOperationException("Este projeto não é to tipo model");
        }

        List<EstruturaDeEntidade> objetos = MapaObjetosProjetoAtual.getListaTodosEstruturaObjeto();
        objetos.forEach(new Consumer<EstruturaDeEntidade>() {
            @Override
            public void accept(EstruturaDeEntidade est) {
                Class classe = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(est.getNomeEntidade());
                FabTipoCodigoDeEntidade tipo = FabTipoCodigoDeEntidade.getTipoProjeto(est);
                switch (tipo) {
                    case EXTENCAO_MODULO_ERP:
                        break;
                    case PROJETO_AUTONOMO:
                    case MODULO_ERP:
                        GeradorReferenciaCampos ref = new GeradorReferenciaCampos(MapaObjetosProjetoAtual.getEstruturaObjeto(classe), classe, contextoERP);
                        ref.salvarEmDiretorioPadraoSubstituindoAnterior();

                        break;
                    default:
                        throw new AssertionError();
                }

                try {

                    if (est.isTemCampoValidadoresLogicos()) {
                        criarAnotacaoValidacao(est);
                    }
                    if (est.isTemCampoValorLogico()) {
                        criarAnotacaoValorLogico(est);
                    }

                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, t.getMessage(), t);
                }
            }
        });

    }

    public void criarAnotacaoValidacao(ItfEstruturaDeEntidade estEstrutura) {
        if (estEstrutura.isUmaEntidadeModuloERP()) {
            if (CarameloCode.isProjetoModuloERP()) {
                UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValidadoresApi(estEstrutura, CarameloCode.isProjetoModuloERP());
            }
        } else {
            UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValidadoresApi(estEstrutura, CarameloCode.isProjetoModuloERP());
        }

        for (ItfEstruturaCampoEntidade pCampo : estEstrutura.getCamposComValidadorLogico()) {
            UtilSBDevelGeradorCodigoModel.homologarClassesDeValidacao(pCampo, CarameloCode.isProjetoModuloERP());
        }

    }

    public void criarAnotacaoValorLogico(ItfEstruturaDeEntidade estEstrutura) {
        boolean contextoERP = CarameloCode.isProjetoModuloERP();
        if (estEstrutura.isUmaEntidadeModuloERP()) {
            if (contextoERP) {
                UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValorLogicaApi(estEstrutura, contextoERP);
            }
        } else {
            UtilSBDevelGeradorCodigoModel.gerarCodigoCampoValorLogicaApi(estEstrutura, CarameloCode.isProjetoModuloERP());
        }

        for (ItfEstruturaCampoEntidade pCampo : estEstrutura.getCamposComValorLogico()) {
            UtilSBDevelGeradorCodigoModel.homologarClassesDeValorLogico(pCampo, contextoERP);
        }
    }

}
