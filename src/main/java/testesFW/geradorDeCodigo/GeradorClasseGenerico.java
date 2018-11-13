/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaSource;

/**
 *
 * @author SalvioF
 */
public abstract class GeradorClasseGenerico extends GeradorGenerico {

    private final JavaClassSource codigoClasseJava;

    public GeradorClasseGenerico(String pPacote, String pNomeClasse) {
        this(pPacote, pNomeClasse, null);

    }

    public GeradorClasseGenerico(String pPacote, String pNomeClasse, String pDiretorioAlternativo) {
        super(pDiretorioAlternativo);
        codigoClasseJava = Roaster.create(JavaClassSource.class);
        codigoClasseJava.setPackage(pPacote);
        codigoClasseJava.setName(pNomeClasse);
    }

    @Override
    public final JavaClassSource getCodigoJava() {
        return codigoClasseJava;
    }

}
