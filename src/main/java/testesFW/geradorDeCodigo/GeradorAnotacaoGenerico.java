/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaAnnotationSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorAnotacaoGenerico extends GeradorGenerico {

    private final JavaAnnotationSource anotacaoSource;

    @Override
    public final JavaAnnotationSource getCodigoJava() {
        return anotacaoSource;
    }

    public GeradorAnotacaoGenerico(String pPacote, String pNomeClasse) {
        super(null);
        anotacaoSource = Roaster.create(JavaAnnotationSource.class);
        anotacaoSource.setName(pNomeClasse);
        anotacaoSource.setPackage(pPacote);
    }

}
