/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaEnumSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorEnumGenerico extends GeradorGenerico {

    private final JavaEnumSource enumgerado;

    @Override
    public final JavaEnumSource getCodigoJava() {
        return enumgerado;
    }

    public GeradorEnumGenerico(String pPacote, String pNomeClasse) {
        super(null);
        enumgerado = Roaster.create(JavaEnumSource.class);
        enumgerado.setName(pNomeClasse);
        enumgerado.setPackage(pPacote);
    }

}
