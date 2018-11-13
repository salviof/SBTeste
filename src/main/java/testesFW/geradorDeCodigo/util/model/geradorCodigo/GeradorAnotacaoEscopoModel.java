/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import testesFW.geradorDeCodigo.GeradorAnotacaoGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorAnotacaoEscopoModel extends GeradorAnotacaoGenerico {

    public GeradorAnotacaoEscopoModel(String pSubPacote, String pNomeClasse) {
        super("org.coletivoJava.fw.projetos." + SBCore.getGrupoProjeto() + ".api.model." + pSubPacote.toLowerCase(), pNomeClasse);

    }

}
