/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.listaDinamica;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringsMaiuculoMinusculo;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.listas.ListaDinamicaGenerica;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorClasseEscopoModel;
import org.jboss.forge.roaster.model.source.AnnotationSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorListasClasseImplementacao extends GeradorClasseEscopoModel {

    public static String gerarSubPacote(ItfEstruturaCampoEntidade pCampo) {
        return (pCampo.getEstruturaPai().getNome());
    }

    public GeradorListasClasseImplementacao(ItfEstruturaCampoEntidade pCampo) {
        super(gerarSubPacote(pCampo), "Lista" + pCampo.getEstruturaPai().getNome() + UtilSBCoreStringsMaiuculoMinusculo.getPrimeiraLetraMaiusculo(pCampo.getNomeDeclarado()));
        try {
            getCodigoJava().extendSuperType(ListaDinamicaGenerica.class);

            GeradorListasAnotacao geradorAnotacao = new GeradorListasAnotacao(pCampo.getEstruturaPai());
            GeradorListasEnum geradorEnum = new GeradorListasEnum(pCampo.getEstruturaPai());
            getCodigoJava().addImport(geradorAnotacao.getCodigoJava());
            getCodigoJava().addImport(geradorEnum.getCodigoJava());
            getCodigoJava().addImport(SBCore.class);

            getCodigoJava().addMethod().setConstructor(true).addParameter(ItfCampoInstanciado.class,
                    "pCampo");

            getCodigoJava().getMethods().forEach(metodo -> {
                if (metodo.isConstructor()) {
                    metodo.setBody("super(pCampo);");
                    System.out.println("Constructor");
                } else {
                    metodo.addAnnotation(Override.class);
                    metodo.setBody("SBCore.getCentralDeMensagens().enviarMsgErroAoUsuario(\"A Validação do campo  "
                            + pCampo.getLabel() + " não foi implementada\");"
                            + "return o;");
                }
            });

            AnnotationSource anotacao = getCodigoJava().addAnnotation(geradorAnotacao.getCodigoJava().getName());
            anotacao.setLiteralValue("lista", geradorEnum.getCodigoJava().getName() + "." + pCampo.getNomeDeclarado().toUpperCase());
            // adicionarReferenciaDeEntidade(pCampo.getEstruturaPai().getNomeEntidade());
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro gerando classe de validação logica" + t.getMessage(), t);
        }
        //String anotcao=geradorAnotacao.getCodigoJava();
        //getCodigoJava().addAnnotation(pCampo.getEstruturaDaEntidade(geradorAnotacao.getCodigoJava())
    }

}
