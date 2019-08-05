/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW;

import com.super_bits.modulosSB.SBCore.modulos.comunicacao.CentralComunicacaoDesktop;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfiguradorCoreDeProjetoJarAbstrato;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;

/**
 *
 * @author desenvolvedor
 */
public class ConfigCoreJunitPadrao extends ConfiguradorCoreDeProjetoJarAbstrato {

    @Override
    public void defineClassesBasicas(ItfConfiguracaoCoreCustomizavel pConfiguracao) {
        super.defineClassesBasicas(pConfiguracao); //To change body of generated methods, choose Tools | Templates.
        pConfiguracao.setCentralComunicacao(CentralComunicacaoDesktop.class);

    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        System.out.println("Nennuma Fabrica de ação foi definida neste projeto, para especificar as ações, extenda" + this.getClass().getSimpleName() + " e sobrescreva o metodo " + Thread.currentThread().getName());
    }

}
