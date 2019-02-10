/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;

/**
 *
 * @author desenvolvedor
 */
public abstract class TestesModelagem extends TesteJunit {

    public void validarAmbiente() {
        if (SBCore.isEmModoDesenvolvimento()) {

        }
    }

    public void criarAnotacoesCalculo() {

    }

    public void criarAnotacoesValidador() {

    }

    public void criarAnotacoesModelagem() {

    }

    public void validarCampo(ItfBeanSimples valor, String campo) {

    }

}
