/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.FabPacoteCRCProjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import testesFW.geradorDeCodigo.GeradorAnotacaoGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorAnotacaoPacoteApiEntidade extends GeradorAnotacaoGenerico {

    public GeradorAnotacaoPacoteApiEntidade(ItfEstruturaDeEntidade pEstrutura, String pNomeClasse) {
        super(pEstrutura.isUmaEntidadeModuloERP() ? FabPacoteCRCProjeto.MODULO_ERP_API_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEstrutura)
                : FabPacoteCRCProjeto.API_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEstrutura), pNomeClasse);

    }

}
