/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.devOps;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreShellBasico;
import cucumber.api.CucumberOptions;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author sfurbino
 */
public class DevOpsCucumberPersistenciaMysql {

    public static String getFeature(Class pClasse) {
        CucumberOptions anotacao = (CucumberOptions) pClasse.getAnnotation(CucumberOptions.class);
        String requisito = anotacao.tags()[0];
        return requisito;
    }

    public static boolean commpilarResultadoRequisito(Class pClasseCucumber) {
        String caminhoExecucao = SBCore.getCaminhoGrupoProjetoSource();

        String slugRequisito = getFeature(pClasseCucumber);
        if (!slugRequisito.startsWith("@")) {
            throw new UnsupportedOperationException("O nome do requisito deve ser compatível com a slug de Feature do cucumber, iniciando com arroba, sem conter espaços");
        }

        InputStream is = DevOpsCucumberPersistenciaMysql.class.getClassLoader().getResourceAsStream("cucumber/devops/compilaBancoCucumber.sh");
        try {
            String caminhoArquivo = caminhoExecucao + "/compilaBancoCucumber.sh";
            IOUtils.copy(is, new FileOutputStream(caminhoArquivo));
            String resposta = UtilSBCoreShellBasico.executeCommand(caminhoArquivo + " " + slugRequisito);
            System.out.println(resposta);
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean carregarResultadoRequisito(Class pClasseCucumber) {

        String caminhoExecucao = SBCore.getCaminhoGrupoProjetoSource();

        String slugRequisito = getFeature(pClasseCucumber);
        if (!slugRequisito.startsWith("@")) {
            throw new UnsupportedOperationException("O nome do requisito deve ser compatível com a slug de Feature do cucumber, iniciando com arroba, sem conter espaços");
        }

        InputStream is = DevOpsCucumberPersistenciaMysql.class.getClassLoader().getResourceAsStream("cucumber/devops/carregaBancoCucumber.sh");
        try {
            String caminhoArquivo = caminhoExecucao + "/carregaBancoCucumber.sh";
            IOUtils.copy(is, new FileOutputStream(caminhoArquivo));
            UtilSBCoreShellBasico.executeCommand("chmod +x " + caminhoArquivo);
            String resposta = UtilSBCoreShellBasico.executeCommand(caminhoArquivo + " " + slugRequisito);
            System.out.println(resposta);
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        return true;

    }

}
