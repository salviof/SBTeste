/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;

/**
 *
 * @author desenvolvedor
 */
public class TesteJUnitBasicoSemAcoes extends TesteJunit {

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreJunitPadraoDevLib(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
    }

}
