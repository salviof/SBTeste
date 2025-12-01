/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.modulos.testes.ItfTesteJunitSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.testes.UtilCRCTestes;

import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public abstract class TesteJunitSBPersistencia extends TesteJunit implements ItfTesteJunitSBPersistencia {

    public static EntityManager getEM() {
        try {
            if (UtilCRCTestes.emContextoTEste == null) {
                UtilCRCTestes.emContextoTEste = SBCore.getServicoRepositorio().gerarNovoEntityManagerPadrao();
            } else if (!UtilCRCTestes.emContextoTEste.isOpen()) {
                UtilCRCTestes.emContextoTEste = SBCore.getServicoRepositorio().gerarNovoEntityManagerPadrao();
            }
            return UtilCRCTestes.emContextoTEste;
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro Obtendo entity manager para pagina", e);

        }
        return null;
    }

    @Override
    public EntityManager renovarConexao() {
        UtilCRCTestes.renovarConexao();
        return UtilCRCTestes.emContextoTEste;

    }

    @Override
    public EntityManager getEMTeste() {
        return getEM();
    }

}
