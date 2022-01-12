/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import java.util.List;

/**
 *
 * @author sfurbino
 */
public class GeradorVariaveisEstaticasEtapasCucumber extends GeradorEnumCucumberEscopoTestes {

    public GeradorVariaveisEstaticasEtapasCucumber(List<EtapaCucumber> pEtapas, String pTagFuncionalidade) {

        super(pTagFuncionalidade, "Etapas" + pTagFuncionalidade);
        pEtapas.forEach(etapa -> {
            final String STR_ETAPA_NOME_VARIVAL = etapa.getNOME_SLUG_VARIAVEL();
            getCodigoJava().addEnumConstant("_" + STR_ETAPA_NOME_VARIVAL);
            getCodigoJava().addField().setPublic()
                    .setType(String.class).setStatic(true).setFinal(true)
                    .setName(STR_ETAPA_NOME_VARIVAL)
                    .setLiteralInitializer("\"" + etapa.getDescritivo() + "\"");
        });

    }

}
