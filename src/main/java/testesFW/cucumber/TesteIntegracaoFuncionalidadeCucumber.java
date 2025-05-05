/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.cucumber;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoStaticDeclare;
import com.super_bits.modulosSB.SBCore.modulos.fonteDados.TokenAcessoDados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.testes.UtilSBCoreTestes;
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

        UtilSBCoreTestes.renovarConexao();

    }

    public static void atualizarEntidadesDeclaradas() {
        renovaConexaoEMAtualizandoEntidadesEstaticasDeclaradas();

    }

    private static void renovaConexaoEMAtualizandoEntidadesEstaticasDeclaradas() {
        for (ItfBeanSimples entidade : UtilSBCoreReflexaoStaticDeclare.getEntidadesEstaticasDeclaradas(CLASSE_FLUXO)) {
            System.out.println("ATUALIZANDO" + entidade);
            SBCore.getServicoRepositorio().getRegistroByID(new TokenAcessoDados(getEM()), MapaObjetosProjetoAtual.getClasseDoObjetoByNome(entidade.getClass().getSimpleName()), entidade.getId());
        }
    }

    @AfterClass
    public static void salvarResultadoFinalBanco() {
        DevOpsCucumberPersistenciaMysql.commpilarResultadoRequisito(CLASSE_FLUXO);
    }
}
