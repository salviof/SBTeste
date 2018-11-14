/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.geradorDeCodigo.erp;

import com.super_bits.modulosSB.SBCore.modulos.erp.ItfApiErpSuperBits;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERP;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorERPImplementacaoContexto extends GeradorClasseGenerico {

    public GeradorERPImplementacaoContexto(ItfApiErpSuperBits pFabrica) {
        super(UtilSBCoreReflexaoAPIERP.getPacoteImplementacaoERP(pFabrica), UtilSBCoreReflexaoAPIERP.getNomeClasseAnotacaoImplementacao(pFabrica) + "impl");
        getCodigoJava().addImport(pFabrica.getInterface());
        getCodigoJava().implementInterface(pFabrica.getInterface());
        getCodigoJava().addAnnotation(pFabrica.getAnotacao());

    }

    @Override
    public void salvarEmDiretorioPadraoSubstituindoAnterior() {
        throw new UnsupportedOperationException("A implementação deve ser gerada sem substituicao da classe anterior");
    }

}
