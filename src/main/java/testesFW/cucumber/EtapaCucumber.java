/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import gherkin.formatter.model.Step;
import java.lang.reflect.Method;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EtapaCucumber {

    private final String tagFuncionalidade;
    private final String descritivo;
    private final String NOME_SLUG_VARIAVEL;
    private final String nomeClasse;
    private final Class anotacaoIndicada;

    public EtapaCucumber(Method pMetodo, String pDescritivoEtapa, String pTagFuncionalidade) {
        descritivo = pDescritivoEtapa;
        String tipo = UtilSBCucumber.getTipoEtapaByMetodo(pMetodo);
        NOME_SLUG_VARIAVEL = UtilSBCucumber.gerarNomeVariavelDaEtapa(tipo, pDescritivoEtapa);
        anotacaoIndicada = UtilSBCucumber.getAnotacaoTipoEtapaDoMetodo(pMetodo);
        nomeClasse = UtilSBCucumber.gerarNomeClasseImplementacaoDaEtapa(tipo, pDescritivoEtapa);
        tagFuncionalidade = pTagFuncionalidade;
    }

    public EtapaCucumber(Step pStep, String pTagFuncinalidade) {
        try {
            descritivo = pStep.getName();
            NOME_SLUG_VARIAVEL = UtilSBCucumber.gerarNomeVariavelDaEtapa(pStep.getKeyword(), descritivo);
            anotacaoIndicada = UtilSBCucumber.getAnotacaoByStep(pStep);
            nomeClasse = UtilSBCucumber.gerarNomeClasseImplementacaoDaEtapa(pStep.getKeyword(), pStep.getName());
            tagFuncionalidade = pTagFuncinalidade;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha criando etapa cucumber", t);
            throw new UnsupportedOperationException("Falha criando dados de etapa cucumber");
        }
    }

    public String getDescritivo() {
        return descritivo;
    }

    public String getNOME_SLUG_VARIAVEL() {
        return NOME_SLUG_VARIAVEL;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public Class getAnotacaoIndicada() {
        return anotacaoIndicada;
    }

    public String getTagFuncionalidade() {
        return tagFuncionalidade;
    }

    @Override
    public String toString() {
        return getNOME_SLUG_VARIAVEL();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EtapaCucumber)) {
            return false;
        }
        return ((EtapaCucumber) obj).getNOME_SLUG_VARIAVEL().equals(getNOME_SLUG_VARIAVEL());

    }

}
