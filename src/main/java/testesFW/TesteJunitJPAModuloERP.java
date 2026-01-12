/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.FabTipoEntidadeGenerico;
import java.util.List;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef.GeradorReferenciaCampos;

/**
 *
 * @author salvio
 */
public abstract class TesteJunitJPAModuloERP extends TesteJunitSBPersistencia {

    @Override
    public void gerarCodigoModelProjeto() {

        if (CarameloCode.isProjetoModuloERP()) {
            gerarCodigoModelERP();
        } else {
            super.gerarCodigoModelProjeto();

        }

    }

    public void gerarCodigoModelERP() {
        List<EstruturaDeEntidade> objetos = MapaObjetosProjetoAtual.getListaTodosEstruturaObjeto();
        objetos.forEach(est -> {
            Class classe = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(est.getNomeEntidade());
            EstruturaDeEntidade estrutura = MapaObjetosProjetoAtual.getEstruturaObjeto(classe);
            if (!estrutura.getModuloERP().equals(FabTipoEntidadeGenerico.MARCADOR_ENTIDADE_TIPO_PROJETO)) {
                if (!classe.isAssignableFrom(ComoAcaoDoSistema.class)) {
                    if (!classe.getSimpleName().startsWith("Acao") && !classe.getSimpleName().startsWith("estrutura")) {

                        GeradorReferenciaCampos ref = new GeradorReferenciaCampos(estrutura, classe, true);
                        ref.salvarEmDiretorioPadraoSubstituindoAnterior();

                    }

                }

                try {
                    if (est.isTemCampoValidadoresLogicos()) {
                        criarAnotacaoValidacao(estrutura);
                    }
                    if (est.isTemCampoValorLogico()) {
                        criarAnotacaoValorLogico(estrutura);
                    }

                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, t.getMessage(), t);
                }
            }
        });

    }

}
