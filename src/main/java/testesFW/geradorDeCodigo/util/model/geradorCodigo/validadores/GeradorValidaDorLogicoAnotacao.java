/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores;

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
public class GeradorValidaDorLogicoAnotacao extends GeradorAnotacaoEscopoModel {

    public GeradorValidaDorLogicoAnotacao(ItfEstruturaDeEntidade pEstrutura) {
        super(pEstrutura.getNomeEntidade(), "Validador" + pEstrutura.getNomeEntidade());
        GeradorValidadorLogicoEnum geradorEnum = new GeradorValidadorLogicoEnum(pEstrutura);
        geradorEnum.getCodigoJava();
        getCodigoJava().addAnnotation(Documented.class);
        getCodigoJava().addAnnotation(Retention.class).setEnumValue(RetentionPolicy.RUNTIME);
        getCodigoJava().addAnnotation(Target.class).setEnumValue(ElementType.TYPE);

        adicionarReferenciaDeEntidade(pEstrutura.getNomeEntidade());

        getCodigoJava().addImport(geradorEnum.getCodigoJava());
        AnnotationElement elemento = getCodigoJava().addAnnotationElement(geradorEnum.getCodigoJava().getName() + " validador();");

        //().addAnnotationElement("validador=");
    }

}
