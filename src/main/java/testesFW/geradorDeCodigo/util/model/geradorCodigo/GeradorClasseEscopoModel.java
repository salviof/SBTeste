/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import testesFW.geradorDeCodigo.GeradorClasseGenerico;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.UtilSBGeradorDeCodigoBase;

/**
 *
 * @author desenvolvedor
 */
public class GeradorClasseEscopoModel extends GeradorClasseGenerico {

    public GeradorClasseEscopoModel(String pSubPacote, String pNomeClasse) {
        super(UtilSBGeradorDeCodigoBase.gerarCaminhoPacoteEscopoModel(pSubPacote), pNomeClasse);
    }

}
