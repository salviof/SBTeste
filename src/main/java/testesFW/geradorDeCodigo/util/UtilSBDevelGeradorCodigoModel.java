/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;

import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.listaDinamica.GeradorListasAnotacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.listaDinamica.GeradorListasClasseImplementacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.listaDinamica.GeradorListasEnum;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidaDorLogicoAnotacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidadorLogicoEnum;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores.GeradorValidicaoLogicaClasseImplementacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicaClasseImplementacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicoAnotacao;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico.GeradorValorLogicoEnum;
import java.io.File;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

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

    public static void gerarCodigoCampoValorLogicaApi(EstruturaDeEntidade estEstrutura) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValorLogicoEnum valorEnum = new GeradorValorLogicoEnum(estEstrutura);
        valorEnum.salvarEmDiretorioPadraoSubstituindoAnterior();
        GeradorValorLogicoAnotacao valorAnotacao = new GeradorValorLogicoAnotacao(estEstrutura);
        valorAnotacao.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static void gerarCodigoCampoListasApi(EstruturaDeEntidade estEstrutura) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorListasEnum valorEnum = new GeradorListasEnum(estEstrutura);
        valorEnum.salvarEmDiretorioPadraoSubstituindoAnterior();
        GeradorListasAnotacao valorAnotacao = new GeradorListasAnotacao(estEstrutura);
        valorAnotacao.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static void gerarCodigoCampoValidadoresApi(EstruturaDeEntidade estEstrutura) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValidadorLogicoEnum enumValidacao = new GeradorValidadorLogicoEnum(estEstrutura);
        enumValidacao.salvarEmDiretorioPadraoSubstituindoAnterior();

        GeradorValidaDorLogicoAnotacao anotacaoValidacao = new GeradorValidaDorLogicoAnotacao(estEstrutura);
        anotacaoValidacao.salvarEmDiretorioPadraoSubstituindoAnterior();
    }

    public static void homologarClassesDeValor(EstruturaCampo pCampo) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValorLogicaClasseImplementacao classeValorLogica = new GeradorValorLogicaClasseImplementacao(pCampo);
        File arquivoLogicaValidacao = new File(classeValorLogica.getCaminhoLocalSalvarCodigo());
        if (!arquivoLogicaValidacao.exists()) {
            if (SBCore.getCentralComunicacao().aguardarRespostaComunicacao(SBCore.getCentralDeComunicacao().getFabricaTransportePadrao().getRegistro(),
                    SBCore.getCentralDeComunicacao().gerarComunicacaoSistema_UsuairoLogado(FabTipoComunicacao.PERGUNTAR_SIM_OU_NAO,
                            "Um O arquivo de Logica para obtenção de resultados do campo " + pCampo.getSlugIdentificador() + " \n não foi encontrado no pacote modelRegraDeNegocio, \n deseja criar esse arquivo?"),
                    0, FabTipoRespostaComunicacao.PERSONALIZADA) == FabTipoRespostaComunicacao.SIM) {
                classeValorLogica.salvarEmDiretorioPadraCASO_NAO_EXISTA();
            }
        }

    }

    public static void homologarClassesDeValidacao(EstruturaCampo pCampo) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorValidicaoLogicaClasseImplementacao validador = new GeradorValidicaoLogicaClasseImplementacao(pCampo);
        File arquivoLogicaValidacao = new File(validador.getCaminhoLocalSalvarCodigo());

        if (!arquivoLogicaValidacao.exists()) {
            if (SBCore.getCentralComunicacao().aguardarRespostaComunicacao(SBCore.getCentralDeComunicacao().getFabricaTransportePadrao().getRegistro(),
                    SBCore.getCentralDeComunicacao().gerarComunicacaoSistema_UsuairoLogado(FabTipoComunicacao.PERGUNTAR_SIM_OU_NAO,
                            "Um O arquivo de validação para o campo " + pCampo.getSlugIdentificador() + " \n não foi encontrado no pacote modelRegraDeNegocio, \n deseja criar esse arquivo?"),
                    0, FabTipoRespostaComunicacao.PERSONALIZADA) == FabTipoRespostaComunicacao.SIM) {
                validador.salvarEmDiretorioPadraCASO_NAO_EXISTA();
            }

        }
    }

    public static void gerarCodigoCampoValorLogico() {

    }

    public static void gerarCodigoCampoListaDinamica(EstruturaCampo estEstrutura) {
        if (!validarAmbienteModel()) {
            return;
        }
        GeradorListasClasseImplementacao enumValidacao = new GeradorListasClasseImplementacao(estEstrutura);
        enumValidacao.salvarEmDiretorioPadraoSubstituindoAnterior();

    }

}