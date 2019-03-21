/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfValidacao;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.UtilSBGeradorDeCodigoBase;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ErroValidacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ValidacaoGenerica;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorClasseEscopoModel;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValidicaoLogicaClasseImplementacao extends GeradorClasseEscopoModel {

    public GeradorValidicaoLogicaClasseImplementacao(ItfEstruturaCampoEntidade pCampo) {
        super(pCampo.getEstruturaPai().getNome(), UtilSBGeradorDeCodigoBase.getNomeClasseValidacao(pCampo));
        try {
            Class classeObjeto = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(pCampo.getEstruturaPai().getNomeEntidade());
            getCodigoJava().addImport(classeObjeto);
            getCodigoJava().addImport(ValidacaoGenerica.class);
            getCodigoJava().setSuperType(ValidacaoGenerica.class.getSimpleName() + "<" + classeObjeto.getSimpleName() + ">");
            // adicionarReferenciaDeEntidade(pCampo.getEstruturaPai().getNomeEntidade());
            GeradorValidaDorLogicoAnotacao geradorAnotacao = new GeradorValidaDorLogicoAnotacao(pCampo.getEstruturaPai());
            GeradorValidadorLogicoEnum geradorEnum = new GeradorValidadorLogicoEnum(pCampo.getEstruturaPai());
            getCodigoJava().addImport(geradorAnotacao.getCodigoJava());
            getCodigoJava().addImport(geradorEnum.getCodigoJava());
            getCodigoJava().addImport(SBCore.class);

            getCodigoJava().addMethod().setPublic().setConstructor(true).addParameter(ItfCampoInstanciado.class,
                    "pCampo");

            MethodSource metodoValidar = getCodigoJava()
                    .addMethod(ItfValidacao.class.getMethod("validar", Object.class))
                    .setPublic().addThrows(ErroValidacao.class);
            //      metodoValidar.setBody(CAMINHO_PADRAO_PACOTE_IMPLEMENTACAO_MODEL);

            metodoValidar.addAnnotation(Override.class);
            metodoValidar.setBody("SBCore.getCentralDeMensagens().enviarMsgErroAoUsuario(\"A Validação do campo  "
                    + pCampo.getLabel() + " não foi implementada\");"
                    + "return o;");

            getCodigoJava().addMethod().setPublic().setReturnType(classeObjeto)
                    .setName("get" + classeObjeto.getSimpleName()).
                    setBody("return getObjetoDoAtributo();");

            getCodigoJava().getMethods().forEach(metodo -> {
                if (metodo.isConstructor()) {
                    metodo.setBody("super(pCampo);");
                    System.out.println("Constructor");
                }
            });

            AnnotationSource anotacao = getCodigoJava().addAnnotation(geradorAnotacao.getCodigoJava().getName());
            anotacao.setLiteralValue("validador", geradorEnum.getCodigoJava().getName() + "." + pCampo.getNomeDeclarado().toUpperCase());
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro gerando classe de validação logica" + t.getMessage(), t);
        }
        //String anotcao=geradorAnotacao.getCodigoJava();
        //getCodigoJava().addAnnotation(pCampo.getEstruturaDaEntidade(geradorAnotacao.getCodigoJava())
    }

}
