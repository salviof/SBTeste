/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabNomeClassePadraoAtributoEntidade;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabPacoteCRCProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexao;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfValidacao;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.FabTipoEntidadeGenerico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorClassePacoteImplEntidade;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.reflections.ReflectionUtils;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade;
import static com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade.EXTENCAO_MODULO_ERP;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.calculos.ItfCalculoValorLogicoAtributoObjeto;
import testesFW.geradorDeCodigo.util.UtilSBDevelGeradorCodigoModel;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValorLogicoEntidadeImplementacao extends GeradorClassePacoteImplEntidade {

    private boolean ummoduloERP;

    public static String gerarSubPacote(ItfEstruturaCampoEntidade pCampo) {
        return pCampo.getEstruturaPai().getNome();
    }

    public GeradorValorLogicoEntidadeImplementacao(ItfEstruturaCampoEntidade pCampo) {
        super(pCampo.getEstruturaPai(),
                UtilSBDevelGeradorCodigoModel.getNomeClasseValorLogico(pCampo));
        FabTipoCodigoDeEntidade tipoCodigo = FabTipoCodigoDeEntidade.getTipoProjeto(pCampo.getEstruturaPai());
        try {

            switch (tipoCodigo) {
                case EXTENCAO_MODULO_ERP:
                    String nomeCanonico = FabPacoteCRCProjeto.MODULO_ERP_IMPLEMENTACAO_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pCampo.getEstruturaPai());
                    nomeCanonico = nomeCanonico + "." + FabNomeClassePadraoAtributoEntidade.CLASSE_CAMPO_ENTIDADE_VALOR_LOGICO_IMPLMENTACAO_PADRAO_ERP.getNomeClassseAtributoEntidade(pCampo);
                    Class classeERP = (Class<? extends ItfCalculoValorLogicoAtributoObjeto>) ReflectionUtils.forName(nomeCanonico);
                    getCodigoJava().extendSuperType(classeERP);
                    break;
                case PROJETO_AUTONOMO:
                case MODULO_ERP:
                    getCodigoJava().extendSuperType(ValorLogicoCalculoGenerico.class);
                    break;
                default:
                    throw new AssertionError();
            }

            GeradorValorLogicoEntidadeAnotacao geradorAnotacao = null;
            GeradorValorLogicoEntidadeEnum geradorEnum = null;
            geradorAnotacao = new GeradorValorLogicoEntidadeAnotacao(pCampo.getEstruturaPai());
            geradorEnum = new GeradorValorLogicoEntidadeEnum(pCampo.getEstruturaPai());

            getCodigoJava().addImport(geradorAnotacao.getCodigoJava());
            getCodigoJava().addImport(geradorEnum.getCodigoJava());
            Class entidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(pCampo.getEstruturaPai().getNomeEntidade());
            getCodigoJava().addImport(entidade);
            //getCodigoJava().addImport(SBCore.clasrefes);

            getCodigoJava().addMethod().setPublic().setConstructor(true).addParameter(ItfCampoInstanciado.class,
                    "pCampo");

            getCodigoJava().getMethods().forEach(metodo -> {
                if (metodo.isConstructor()) {
                    metodo.setBody("super(pCampo);");
                    System.out.println("Constructor");
                }
            });
            switch (tipoCodigo) {

                case EXTENCAO_MODULO_ERP:
                    getCodigoJava().addMethod("getValor(Object... pEntidade)").setReturnType(Object.class).setPublic().setBody("return super.getValor(pEntidade);").addAnnotation(Override.class);

                    break;
                case PROJETO_AUTONOMO:
                    break;
                case MODULO_ERP:
                    break;
                default:
                    throw new AssertionError();
            }

            String nomeEntidade
                    = pCampo.getEstruturaPai().getNomeEntidade();
            getCodigoJava().addMethod("get" + nomeEntidade + "()")
                    .setPrivate()
                    .setReturnType(nomeEntidade)
                    .setBody("return (" + nomeEntidade + ") getCampoInst().getObjetoRaizDoAtributo();");

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
