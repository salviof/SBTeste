/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.FabPacoteCRCProjeto;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoCodigoDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorClassePacoteImplEntidade extends GeradorClasseGenerico {

    public GeradorClassePacoteImplEntidade(ItfEstruturaDeEntidade pEstrutura, String pNomeClasse) {

        super(FabTipoCodigoDeEntidade.getTipoProjeto(pEstrutura).isTipoCodigoERP(pEstrutura) ? FabPacoteCRCProjeto.MODULO_ERP_IMPLEMENTACAO_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEstrutura)
                : FabPacoteCRCProjeto.IMPLEMENTACAO_ESTRUTURA_ENTIDADE.getPacoteCanonicoDeEntidade(pEstrutura), pNomeClasse
        );

    }

}
