/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabNomeClassePadraoAtributoEntidade;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabPacoteCRCProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade;
import static com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade.MODULO_ERP;
import static com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade.PROJETO_AUTONOMO;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfValidacao;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ErroValidacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ValidacaoGenerica;
import java.util.ArrayList;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorClassePacoteImplEntidade;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import testesFW.geradorDeCodigo.util.UtilSBDevelGeradorCodigoModel;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValidicaoLogicaClasseImplementacao extends GeradorClassePacoteImplEntidade {

    public GeradorValidicaoLogicaClasseImplementacao(ItfEstruturaCampoEntidade pCampo, boolean pValidacao) {
        super(pCampo.getEstruturaPai(),
                UtilSBDevelGeradorCodigoModel.getNomeClasseValidacao(pCampo)
        );
        try {
            Class classeObjeto = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(pCampo.getEstruturaPai().getNomeEntidade());

            FabTipoCodigoDeEntidade tipo = FabTipoCodigoDeEntidade.getTipoProjeto(pCampo.getEstruturaPai());

            switch (tipo) {

                case EXTENCAO_MODULO_ERP:

                    String pacote = FabPacoteCRCProjeto.MODULO_ERP_IMPLEMENTACAO_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pCampo.getEstruturaPai());
                    String nome = FabNomeClassePadraoAtributoEntidade.CLASSE_CAMPO_ENTIDADE_VALIDACAO_IMPLEMENTACAO_PADRAO_ERP.getNomeClassseAtributoEntidade(pCampo);
                    String extencao = pacote + "." + nome; //+ "<" + classeObjeto.getSimpleName() + ">";
                    getCodigoJava().setSuperType(extencao);
                    break;
                case PROJETO_AUTONOMO:
                case MODULO_ERP:
                    getCodigoJava().addImport(ValidacaoGenerica.class);
                    getCodigoJava().setSuperType(ValidacaoGenerica.class.getSimpleName() + "<" + classeObjeto.getSimpleName() + ">");
                    break;
                default:
                    throw new AssertionError();
            }

            getCodigoJava().addImport(classeObjeto);

            // adicionarReferenciaDeEntidade(pCampo.getEstruturaPai().getNomeEntidade());
            GeradorValidaDorLogicoAnotacao geradorAnotacao = null;
            GeradorValidadorLogicoEnum geradorEnum = null;
            switch (tipo) {
                case MODULO_ERP:
                case EXTENCAO_MODULO_ERP:
                    geradorAnotacao = new GeradorValidaDorLogicoAnotacao(pCampo.getEstruturaPai(), true);
                    geradorEnum = new GeradorValidadorLogicoEnum(pCampo.getEstruturaPai(), true);
                    break;
                case PROJETO_AUTONOMO:
                    geradorAnotacao = new GeradorValidaDorLogicoAnotacao(pCampo.getEstruturaPai(), false);
                    geradorEnum = new GeradorValidadorLogicoEnum(pCampo.getEstruturaPai(), false);

                    break;
                default:
                    throw new AssertionError();
            }

            getCodigoJava().addImport(geradorAnotacao.getCodigoJava());
            getCodigoJava().addImport(geradorEnum.getCodigoJava());
            getCodigoJava().addImport(SBCore.class);
            getCodigoJava().addImport(ArrayList.class);
            getCodigoJava().addImport(CarameloCode.class);
            getCodigoJava().addMethod().setPublic().setConstructor(true).addParameter(ItfCampoInstanciado.class,
                    "pCampo");

            MethodSource metodoValidar = getCodigoJava()
                    .addMethod(ItfValidacao.class.getMethod("validar", Object.class))
                    .setPublic().addThrows(ErroValidacao.class);
            //      metodoValidar.setBody(CAMINHO_PADRAO_PACOTE_IMPLEMENTACAO_MODEL);
            metodoValidar.setAbstract(false);
            metodoValidar.addAnnotation(Override.class);

            switch (tipo) {

                case EXTENCAO_MODULO_ERP:
                    metodoValidar.setBody("return super.validar(o);");
                    break;
                case PROJETO_AUTONOMO:
                case MODULO_ERP:
                    metodoValidar.setBody(
                            " \n CarameloCode.getServicoMensagemFireForget().enviarMsgErroAoUsuario(\"A Validação do campo  "
                            + pCampo.getLabelPadrao() + " não foi implementada\");\n"
                            + "return new ArrayList<>();");
                    break;
                default:
                    throw new AssertionError();
            }

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
