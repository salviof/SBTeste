/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumEscopoModel;
import java.lang.reflect.Field;

/**
 *
 * @author desenvolvedor
 */
public class GeradorReferenciaCampos extends GeradorEnumEscopoModel {

    public GeradorReferenciaCampos(Class<? extends ItfBeanSimples> entidade) {
        super(entidade.getSimpleName(), "CP" + entidade.getSimpleName());
        adicionarReferenciaDeEntidade(entidade.getSimpleName());
        for (Field campo : entidade.getDeclaredFields()) {
            getCodigoJava().addEnumConstant("_" + campo.getName().toUpperCase());
            getCodigoJava().addField().setPublic().setType(String.class).setStatic(true).setFinal(true).setName(campo.getName().toLowerCase()).setLiteralInitializer("\"" + campo.getName() + "\"");
        }

    }

}
