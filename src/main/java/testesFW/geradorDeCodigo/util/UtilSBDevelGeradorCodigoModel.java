/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabNomeClassePadraoAtributoEntidade;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;

import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;

import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidaDorLogicoAnotacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidadorLogicoEnum;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidicaoLogicaClasseImplementacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicoEntidadeImplementacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicoEntidadeAnotacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicoEntidadeEnum;
import java.io.File;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBDevelGeradorCodigoModel {

    public static boolean validarAmbienteModel() {
        return (SBCore.isEmModoDesenvolvimento() && FabTipoProjeto.MODEL_E_CONTROLLER.equals(SBCore.getTipoProjeto()));
    }

    public static void gerarCodigoGetAcoesGestao(ItfAcaoGerenciarEntidade pAcao) {
        if (!validarAmbienteModel()) {
            return;
        }
        String codigo = "";

        JavaClassSource classeGetGestao = Roaster.create(JavaClassSource.class);

        classeGetGestao.setPackage("org.coletivoJava.superBitsFW.webPaginas.config");
        classeGetGestao.addAnnotation();

    }

    public static void gerarCodigoCampoValorLogicaApi(ItfEstruturaDeEntidade estEstrutura, boolean pModoERP) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValorLogicoEntidadeEnum valorEnum = new GeradorValorLogicoEntidadeEnum(estEstrutura);
        valorEnum.salvarEmDiretorioPadraoSubstituindoAnterior();
        GeradorValorLogicoEntidadeAnotacao valorAnotacao = new GeradorValorLogicoEntidadeAnotacao(estEstrutura);
        valorAnotacao.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static void gerarCodigoCampoValidadoresApi(ItfEstruturaDeEntidade estEstrutura, boolean pModoERP) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValidadorLogicoEnum enumValidacao = new GeradorValidadorLogicoEnum(estEstrutura, pModoERP);
        enumValidacao.salvarEmDiretorioPadraoSubstituindoAnterior();

        GeradorValidaDorLogicoAnotacao anotacaoValidacao = new GeradorValidaDorLogicoAnotacao(estEstrutura, pModoERP);
        anotacaoValidacao.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static void homologarClassesDeValorLogico(ItfEstruturaCampoEntidade pCampo, boolean pModoERP) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValorLogicoEntidadeImplementacao classeValorLogica = new GeradorValorLogicoEntidadeImplementacao(pCampo);
        File arquivoLogicaValidacao = new File(classeValorLogica.getCaminhoLocalSalvarCodigo());
        if (!arquivoLogicaValidacao.exists()) {
            if (SBCore.getServicoComunicacao().aguardarRespostaComunicacao(SBCore.getServicoComunicacao().getFabricaCanalPadrao().getRegistro(),
                    SBCore.getServicoComunicacao().gerarComunicacaoSistema_UsuarioLogado(FabTipoComunicacao.PERGUNTAR_SIM_OU_NAO,
                            "Um O arquivo de logica    para o campo " + pCampo.getSlugIdentificador() + " \n não foi encontrado no pacote modelRegraDeNegocio, \n deseja criar esse arquivo?"),
                    0, FabTipoRespostaComunicacao.PERSONALIZADA) == FabTipoRespostaComunicacao.SIM) {
                classeValorLogica.salvarEmDiretorioPadraCASO_NAO_EXISTA();
            }
        }

    }

    public static void homologarClassesDeValidacao(ItfEstruturaCampoEntidade pCampo, boolean pModoERP) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValidicaoLogicaClasseImplementacao validador = new GeradorValidicaoLogicaClasseImplementacao(pCampo, pModoERP);
        File arquivoLogicaValidacao = new File(validador.getCaminhoLocalSalvarCodigo());

        if (!arquivoLogicaValidacao.exists()) {
            if (SBCore.getServicoComunicacao().aguardarRespostaComunicacao(SBCore.getServicoComunicacao().getFabricaCanalPadrao().getRegistro(),
                    SBCore.getServicoComunicacao().gerarComunicacaoSistema_UsuarioLogado(FabTipoComunicacao.PERGUNTAR_SIM_OU_NAO,
                            "Um O arquivo de validação para o campo " + pCampo.getSlugIdentificador() + " \n não foi encontrado no pacote modelRegraDeNegocio, \n deseja criar esse arquivo?"),
                    0, FabTipoRespostaComunicacao.PERSONALIZADA) == FabTipoRespostaComunicacao.SIM) {
                validador.salvarEmDiretorioPadraCASO_NAO_EXISTA();
            }

        }
    }

    public static void gerarCodigoCampoValorLogico() {

    }

    public static String getNomeClasseValidacao(ItfEstruturaCampoEntidade pCampo) {

        return FabNomeClassePadraoAtributoEntidade.CLASSE_CAMPO_ENTIDADE_VALIDACAO.getNomeClassseAtributoEntidade(pCampo);

    }

    public static String getNomeClasseValorLogico(ItfEstruturaCampoEntidade pCampo) {

        return FabNomeClassePadraoAtributoEntidade.CLASSE_CAMPO_ENTIDADE_VALOR_LOGICO.getNomeClassseAtributoEntidade(pCampo);

    }

}
