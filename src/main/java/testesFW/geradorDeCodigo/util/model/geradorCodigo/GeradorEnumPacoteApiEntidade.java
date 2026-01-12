/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.FabPacoteCRCProjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import testesFW.geradorDeCodigo.GeradorEnumGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorEnumPacoteApiEntidade extends GeradorEnumGenerico {

    public GeradorEnumPacoteApiEntidade(ItfEstruturaDeEntidade pEntidade, String pNomeClasse, boolean pModoModuloERP) {

        super(pModoModuloERP ? FabPacoteCRCProjeto.MODULO_ERP_API_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEntidade)
                : FabPacoteCRCProjeto.API_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEntidade),
                pNomeClasse);

    }

}
