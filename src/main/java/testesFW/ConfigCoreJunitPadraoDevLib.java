/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;

import com.super_bits.modulosSB.SBCore.modulos.comunicacao.CentralComunicacaoDesktopTransient;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ComunicacaoTransient;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ComunicacaoTransientUsrToUsr;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;

/**
 *
 * @author desenvolvedorninja01
 * @since 25/10/2019
 * @version 1.0
 */
public class ConfigCoreJunitPadraoDevLib extends ConfigCoreJunitPadraoDevAcoes {

    @Override
    public void defineClassesBasicas(ItfConfiguracaoCoreCustomizavel pConfiguracao) {
        super.defineClassesBasicas(pConfiguracao); //chamada super do metodo (implementação classe pai)
        pConfiguracao.setCentralComunicacao(CentralComunicacaoDesktopTransient.class);
    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        pConfig.setCentralComunicacao(CentralComunicacaoDesktopTransient.class);
        super.defineFabricasDeACao(pConfig);
        setIgnorarConfiguracaoAcoesDoSistema(true);
        setIgnorarConfiguracaoPermissoes(true);

    }

}
