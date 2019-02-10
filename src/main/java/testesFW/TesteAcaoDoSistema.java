/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilFabricaDeAcoesBasico;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;

/**
 *
 * Classe para executar testes em ações do sistemas
 *
 * @author sfurbino
 */
public abstract class TesteAcaoDoSistema extends TesteJunit {

    private boolean validarAcoesNaoConfiguradas = false;

    private boolean validarAcao(ItfAcaoDoSistema pAcaoDoSistema) {

        try {

            UtilFabricaDeAcoesBasico.validaIntegridadeAcaoDoSistema(pAcaoDoSistema);

            return true;
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
        return false;
    }

    /**
     *
     * @param pValidarAcoesNaoCOnfiguradas True para validar todas as ações,
     * False para validar apenas aquelas que foram configuradas
     */
    public TesteAcaoDoSistema(boolean pValidarAcoesNaoCOnfiguradas) {
        validarAcoesNaoConfiguradas = pValidarAcoesNaoCOnfiguradas;
    }

    public void testesBasicosDeAcoes(Class pFabricaDeAcoes) {
        boolean proseguiuSemErro = true;

        for (Object obj : pFabricaDeAcoes.getEnumConstants()) {
            try {

                ItfFabricaAcoes fabrica = (ItfFabricaAcoes) obj;
                ItfAcaoDoSistema novaAcao = fabrica.getRegistro();

                if (novaAcao == null) {
                    throw new UnsupportedOperationException("Ação " + pFabricaDeAcoes.toString() + " retornou nulo a partir da fábrica");
                }

                if (fabrica.getEntidadeDominio() == null) {
                    throw new UnsupportedOperationException("Entidade da ação " + pFabricaDeAcoes.toString() + " não foi definido");
                }
                if (fabrica.getNomeModulo() == null) {
                    throw new UnsupportedOperationException("O módulo da ação " + pFabricaDeAcoes.toString() + " não foi definido");
                }

                validarAcao(novaAcao);

            } catch (Throwable t) {
                proseguiuSemErro = false;
                lancarErroJUnit(t);
            }

            assertTrue("Aconteceu um erro na ação " + obj, proseguiuSemErro);

        }

    }

}
