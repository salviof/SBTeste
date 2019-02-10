/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.geradorDeCodigo.erp;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.modulos.erp.InfoReferenciaApiErp;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfApiErpSuperBits;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERP;
import testesFW.geradorDeCodigo.GeradorAnotacaoGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorAPIERP extends GeradorAnotacaoGenerico {

    private final ItfApiErpSuperBits fab;

    public GeradorAPIERP(ItfApiErpSuperBits pFabrica) {
        this(pFabrica, null);

    }

    public GeradorAPIERP(ItfApiErpSuperBits pFabrica, String pSlug) {
        super(UtilSBCoreReflexaoAPIERP.getPacoteApiClasseAnotacao(pFabrica), UtilSBCoreReflexaoAPIERP.getNomeClasseAnotacaoImplementacao(pFabrica) + UtilSBCoreStringFiltros.getNuloEmEmpty(pSlug));
        getCodigoJava().addAnnotation(Qualifier.class);
        getCodigoJava().addImport(pFabrica.getInterface());
        getCodigoJava().addImport(InfoReferenciaApiErp.class);
        getCodigoJava().addImport(pFabrica.getClass());
        getCodigoJava().addAnnotation(InfoReferenciaApiErp.class).setLiteralValue("tipoObjeto ", pFabrica.getClass().getSimpleName() + ".class");
        getCodigoJava().addAnnotation(Documented.class);
        getCodigoJava().addAnnotation(Retention.class).setEnumValue(RetentionPolicy.RUNTIME);
        getCodigoJava().addAnnotation(Target.class).setEnumValue(ElementType.TYPE);
        if (!SBCore.getGrupoProjeto().equals("erpColetivoJava")) {
            throw new UnsupportedOperationException("Esta geração deve estar organizada no grupo erpColetivoJava");
        }
        fab = pFabrica;
    }

    public final void salvarAnotacoesComplementares() {
        new GeradorAPIERP(getFab(), "Testes").salvarEmDiretorioPadraoSubstituindoAnterior(true);
        new GeradorAPIERP(getFab(), "Padrao").salvarEmDiretorioPadraoSubstituindoAnterior(true);
    }

    public final void salvarEmDiretorioPadraoSubstituindoAnterior(boolean salvarComplemento) {
        if (salvarComplemento) {
            super.salvarEmDiretorioPadraoSubstituindoAnterior();//To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public final void salvarEmDiretorioPadraoSubstituindoAnterior() {
        super.salvarEmDiretorioPadraoSubstituindoAnterior(); //To change body of generated methods, choose Tools | Templates.
        salvarAnotacoesComplementares();
    }

    public ItfApiErpSuperBits getFab() {
        return fab;
    }

}
