/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesFW.devOps;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCShellBasico;
import cucumber.api.CucumberOptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        InputStream is = DevOpsCucumberPersistenciaMysql.class.getClassLoader()
                .getResourceAsStream("cucumber/devops/compilaBancoCucumber.sh");

        String caminhoArquivo = caminhoExecucao + "/compilaBancoCucumber.sh";
        File scriptFile = new File(caminhoArquivo);

        try {
            // === MELHORIA FORTE PARA EVITAR "ÁREA DE TEXTO OCUPADA" ===
            if (scriptFile.exists()) {
                scriptFile.delete();                    // remove o antigo
            }

            try (FileOutputStream fos = new FileOutputStream(scriptFile)) {
                IOUtils.copy(is, fos);
                fos.flush();
            }

            scriptFile.setExecutable(true, false);

            // Força o Linux a liberar o arquivo
            Runtime.getRuntime().exec("sync").waitFor();
            Thread.sleep(350);   // ← aumentado (importante)

            // Chama exatamente como você fazia antes
            String resposta = UtilCRCShellBasico.executeCommand(true, true, caminhoArquivo + " " + slugRequisito);
            System.out.println(resposta);

        } catch (Exception ex) {
            ex.printStackTrace();
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
            //  UtilCRCShellBasico.executeCommand("chmod +x " + caminhoArquivo);
            String resposta = UtilCRCShellBasico.executeCommand(caminhoArquivo + " " + slugRequisito);
            System.out.println(resposta);
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        return true;

    }

}
