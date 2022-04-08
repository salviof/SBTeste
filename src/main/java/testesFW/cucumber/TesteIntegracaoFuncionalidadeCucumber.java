/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import cucumber.api.java.After;
import java.util.HashMap;
import java.util.Map;
import testesFW.ItfTestesSBCore;
import testesFW.TesteJunitSBPersistencia;
import testesFW.devOps.DevOpsCucumberPersistenciaMysql;

/**
 *
 * @author sfurbino
 */
public abstract class TesteIntegracaoFuncionalidadeCucumber extends TesteJunitSBPersistencia implements ItfTestesSBCore {

    Map<String, Object> mapaObjetosDaIntegracao = new HashMap<>();

    public static void renovarConexaoEntityManagerEscopoTeste() {

        TesteJunitSBPersistencia teste = new TesteJunitSBPersistencia() {
            @Override
            protected void configAmbienteDesevolvimento() {

            }
        };
        teste.renovarConexao();
    }

    public void salvarResultadoFinalBanco() {

    }

}
