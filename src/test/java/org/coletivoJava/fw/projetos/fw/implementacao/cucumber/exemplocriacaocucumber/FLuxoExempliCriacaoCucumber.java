/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coletivoJava.fw.projetos.fw.implementacao.cucumber.exemplocriacaocucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import testesFW.ConfigCoreJunitPadraoDevLib;
import testesFW.cucumber.CucumberSBTestes;
import testesFW.cucumber.TesteIntegracaoFuncionalidadeCucumber;
import testesFW.devOps.DevOpsCucumberPersistenciaMysql;

/**
 *
 * @author salvio
 */
@RunWith(CucumberSBTestes.class)
@CucumberOptions(
        features = "classpath:cucumber", tags = "@ExemploCriacaoCucumber",
        glue = "org.coletivoJava.fw.projetos.fw.implementacao.cucumber.exemplocriacaocucumber.etapas",
        monochrome = false, dryRun = false)
public class FLuxoExempliCriacaoCucumber extends TesteIntegracaoFuncionalidadeCucumber {

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreJunitPadraoDevLib(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
    }

}
