/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico;

import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorAnotacaoEscopoModel;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jboss.forge.roaster.model.AnnotationElement;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValorLogicoAnotacao extends GeradorAnotacaoEscopoModel {

    public GeradorValorLogicoAnotacao(ItfEstruturaDeEntidade pEstrutura) {
        super(pEstrutura.getNomeEntidade(), "ValorLogico" + pEstrutura.getNomeEntidade());
        GeradorValorLogicoEnum geradorEnum = new GeradorValorLogicoEnum(pEstrutura);
        geradorEnum.getCodigoJava();
        getCodigoJava().addAnnotation(Documented.class);
        getCodigoJava().addAnnotation(Retention.class).setEnumValue(RetentionPolicy.RUNTIME);
        getCodigoJava().addAnnotation(Target.class).setEnumValue(ElementType.TYPE);

        getCodigoJava().addImport(geradorEnum.getCodigoJava());
        AnnotationElement elemento = getCodigoJava().addAnnotationElement(geradorEnum.getCodigoJava().getName() + " calculo();");
        adicionarReferenciaDeEntidade(pEstrutura.getNomeEntidade());
        //().addAnnotationElement("validador=");
    }

}
