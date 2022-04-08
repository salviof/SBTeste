/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import testesFW.geradorDeCodigo.GeradorEnumGenerico;

/**
 *
 * @author sfurbino
 */
public class GeradorEnumCucumberEscopoTestes extends GeradorEnumGenerico {

    public GeradorEnumCucumberEscopoTestes(String pSubPacote, String pNomeClasse) {
        super("org.coletivoJava.fw.projetos." + SBCore.getGrupoProjeto() + ".api.cucumber." + pSubPacote.toLowerCase(), pNomeClasse, TIPO_PACOTE.TESTES);
    }

}
