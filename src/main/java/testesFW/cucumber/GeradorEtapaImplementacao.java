/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.ArrayList;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author sfurbino
 */
public class GeradorEtapaImplementacao extends GeradorClasseGenerico {

    public GeradorEtapaImplementacao(String pSubPacote, EtapaCucumber pEtapa) {
        super("org.coletivoJava.fw.projetos." + SBCore.getGrupoProjeto() + ".implementacao.cucumber." + pSubPacote.toLowerCase() + ".etapas",
                pEtapa.getNomeClasse(),
                TIPO_PACOTE.TESTES);
        try {

            GeradorVariaveisEstaticasEtapasCucumber geradorEtapas = new GeradorVariaveisEstaticasEtapasCucumber(new ArrayList(), pSubPacote);
            String nomeClasseCompleto = geradorEtapas.getCodigoJava().getCanonicalName();
            getCodigoJava().addImport(nomeClasseCompleto);
            String nomeClass = geradorEtapas.getCodigoJava().getName();

            getCodigoJava().addImport(pEtapa.getAnotacaoIndicada());
            getCodigoJava().addImport(UnsupportedOperationException.class);
            getCodigoJava().addMethod("public void implementacaoEtapa()");

            getCodigoJava().getMethods().get(0).addAnnotation(pEtapa.getAnotacaoIndicada());
            getCodigoJava().getMethods().get(0).setBody("throw new UnsupportedOperationException(\"Etapa '" + pEtapa.getDescritivo() + "' não implementadas\");");
            getCodigoJava().getMethods().get(0).getAnnotations().get(0).setLiteralValue(nomeClass + "." + pEtapa.getNOME_SLUG_VARIAVEL());
        } catch (Throwable t) {
            System.out.println(pEtapa);
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro Gerando etapa" + t.getMessage(), t);
        }

    }

}
