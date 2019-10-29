/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.CentralComunicacaoDesktop;

/**
 *
 * @author desenvolvedorninja01
 * @since 25/10/2019
 * @version 1.0
 */
public class ConfigCoreJunitPadraoDesenvolvedor extends ConfigCoreJunitPadrao {

    @Override
    public void defineClassesBasicas(ItfConfiguracaoCoreCustomizavel pConfiguracao) {
        super.defineClassesBasicas(pConfiguracao); //chamada super do metodo (implementação classe pai)
        pConfiguracao.setCentralComunicacao(CentralComunicacaoDesktop.class);
    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        pConfig.setCentralComunicacao(CentralComunicacaoDesktop.class);
        super.defineFabricasDeACao(pConfig); //chamada super do metodo (implementação classe pai)
    }

}
