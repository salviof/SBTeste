/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringsMaiuculoMinusculo;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorClasseEscopoModel;
import org.jboss.forge.roaster.model.source.AnnotationSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValorLogicaClasseImplementacao extends GeradorClasseEscopoModel {

    public static String gerarSubPacote(ItfEstruturaCampoEntidade pCampo) {
        return pCampo.getEstruturaPai().getNome();
    }

    public GeradorValorLogicaClasseImplementacao(ItfEstruturaCampoEntidade pCampo) {
        super(gerarSubPacote(pCampo), "ValorLogico" + pCampo.getEstruturaPai().getNome() + UtilSBCoreStringsMaiuculoMinusculo.getPrimeiraLetraMaiusculo(pCampo.getNomeDeclarado()));
        try {
            getCodigoJava().extendSuperType(ValorLogicoCalculoGenerico.class);

            GeradorValorLogicoAnotacao geradorAnotacao = new GeradorValorLogicoAnotacao(pCampo.getEstruturaPai());
            GeradorValorLogicoEnum geradorEnum = new GeradorValorLogicoEnum(pCampo.getEstruturaPai());
            getCodigoJava().addImport(geradorAnotacao.getCodigoJava());
            getCodigoJava().addImport(geradorEnum.getCodigoJava());
            //getCodigoJava().addImport(SBCore.clasrefes);

            getCodigoJava().addMethod().setConstructor(true).addParameter(ItfCampoInstanciado.class,
                    "pCampo");

            getCodigoJava().getMethods().forEach(metodo -> {
                if (metodo.isConstructor()) {
                    metodo.setBody("super(pCampo);");
                    System.out.println("Constructor");
                } else {
                    //  metodo.addAnnotation(Override.class);
                    // metodo.setBody("SBCore.getCentralDeMensagens().enviarMsgErroAoUsuario(\"O valor Logico do campo  "
                    //         + pCampo.getLabel() + " não foi implementada\");"
                    //         + "return o;");
                }
            });
            //adicionarReferenciaDeEntidade(pCampo.getEstruturaPai().getNomeEntidade());
            AnnotationSource anotacao = getCodigoJava().addAnnotation(geradorAnotacao.getCodigoJava().getName());
            anotacao.setLiteralValue("calculo", geradorEnum.getCodigoJava().getName() + "." + pCampo.getNomeDeclarado().toUpperCase());
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro gerando classe de validação logica" + t.getMessage(), t);
        }
        //String anotcao=geradorAnotacao.getCodigoJava();
        //getCodigoJava().addAnnotation(pCampo.getEstruturaDaEntidade(geradorAnotacao.getCodigoJava())
    }

}
