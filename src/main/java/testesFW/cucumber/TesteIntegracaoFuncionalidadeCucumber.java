/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexaoStaticDeclare;
import com.super_bits.modulosSB.SBCore.modulos.fonteDados.TokenAcessoDados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.testes.UtilCRCTestes;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import testesFW.ItfTestesSBCore;
import testesFW.TesteJunitSBPersistencia;
import testesFW.devOps.DevOpsCucumberPersistenciaMysql;

/**
 *
 * @author sfurbino
 */
public abstract class TesteIntegracaoFuncionalidadeCucumber extends TesteJunitSBPersistencia implements ItfTestesSBCore {

    protected static Class CLASSE_FLUXO;

    public static void renovarConexaoEntityManagerEscopoTeste() {

        UtilCRCTestes.renovarConexao();

    }

    public static void atualizarEntidadesDeclaradas() {
        renovaConexaoEMAtualizandoEntidadesEstaticasDeclaradas();

    }

    private static void renovaConexaoEMAtualizandoEntidadesEstaticasDeclaradas() {
        UtilCRCTestes.renovarConexao();
        List<Field> campos = UtilCRCReflexaoStaticDeclare.getObjetosEstaticosDestaClasse(CLASSE_FLUXO, ComoEntidadeSimples.class);

        for (Field cp : campos) {
            ComoEntidadeSimples entidade;
            try {
                entidade = (ComoEntidadeSimples) cp.get(null);
                if (entidade != null) {
                    System.out.println("ATUALIZANDO" + entidade);

                    cp.set(null, SBCore.getServicoRepositorio().getEntidadeByID(new TokenAcessoDados(getEM()), MapaObjetosProjetoAtual.getClasseDoObjetoByNome(entidade.getClass().getSimpleName()), entidade.getId()));
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(TesteIntegracaoFuncionalidadeCucumber.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(TesteIntegracaoFuncionalidadeCucumber.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @AfterClass
    public static void salvarResultadoFinalBanco() {
        DevOpsCucumberPersistenciaMysql.commpilarResultadoRequisito(CLASSE_FLUXO);
    }
}
