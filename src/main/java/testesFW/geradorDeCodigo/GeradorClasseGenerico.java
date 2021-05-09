/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author SalvioF
 */
public abstract class GeradorClasseGenerico extends GeradorGenerico {

    private final JavaClassSource codigoClasseJava;

    public GeradorClasseGenerico(String pPacote, String pNomeClasse) {
        this(pPacote, pNomeClasse, null, TIPO_PACOTE.IMPLEMENTACAO);

    }

    public GeradorClasseGenerico(String pPacote, String pNomeClasse, TIPO_PACOTE pTipo) {
        this(pPacote, pNomeClasse, null, pTipo);

    }

    public GeradorClasseGenerico(String pPacote, String pNomeClasse, String pDiretorioAlternativo, TIPO_PACOTE pTipoPacote) {
        super(pDiretorioAlternativo, pTipoPacote);
        codigoClasseJava = Roaster.create(JavaClassSource.class);
        codigoClasseJava.setPackage(pPacote);
        codigoClasseJava.setName(pNomeClasse);
    }

    @Override
    public final JavaClassSource getCodigoJava() {
        return codigoClasseJava;
    }

}
