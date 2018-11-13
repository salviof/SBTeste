/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import testesFW.geradorDeCodigo.GeradorEnumGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorEnumEscopoModel extends GeradorEnumGenerico {

    public GeradorEnumEscopoModel(String pSubPacote, String pNomeClasse) {
        super("org.coletivoJava.fw.projetos." + SBCore.getGrupoProjeto() + ".api.model." + pSubPacote.toLowerCase(), pNomeClasse);

    }

}
