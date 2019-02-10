/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 *
 * @author SalvioF
 */
public abstract class GeradorInterfaceGenerico extends GeradorGenerico {

    private final JavaInterfaceSource codigoClasseJava;

    public GeradorInterfaceGenerico(String pPacote, String pNomeClasse) {
        this(pPacote, pNomeClasse, null);

    }

    public GeradorInterfaceGenerico(String pPacote, String pNomeClasse, String pDiretorioAlternativo) {
        super(pDiretorioAlternativo);
        codigoClasseJava = Roaster.create(JavaInterfaceSource.class);
        codigoClasseJava.setPackage(pPacote);
        codigoClasseJava.setName(pNomeClasse);
    }

    @Override
    public final JavaInterfaceSource getCodigoJava() {
        return codigoClasseJava;
    }

}
