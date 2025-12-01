/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTILSBCoreDesktopApp;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilCRCArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.modulosSB.SBCore.modulos.tratamentoErros.ItfInfoErroSB;
import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.ItfRelatorioTestes;
import org.coletivojava.fw.api.objetoNativo.mensagem.MensagemProgramador;
import testesFW.TesteJunit;
import java.util.List;

/**
 *
 * @author desenvolvedor
 */
public abstract class RelatorioTesteAbstrato extends TesteJunit implements ItfRelatorioTestes {

    protected void exibirEGravarRelatorioDeErro(List<ItfInfoErroSB> erros) {

        String mensagem = "OS SEGUINTES ERROS DE CONFIGURAÇÃO FORAM ENCONTRADOS NO SISTEMA: \n";
        mensagem += " Você pode encontrar informações mais detalhadas do erro no SystemOut \n";
        for (ItfInfoErroSB erro : erros) {
            mensagem += ("->" + erro.getMsgDesenvolvedorLancou() + "\n");
        }

        if (erros.isEmpty()) {
            mensagem = "Nenhum erro foi encontrado :D, Bom Trabalho!";
            UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemProgramador(mensagem));
        } else {
            UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemProgramador(mensagem, FabMensagens.ERRO));
        }

        String arquivoRelatorio = SBCore.getCaminhoDesenvolvimento() + "/temp/errosAcoes.txt";
        UtilCRCArquivoTexto.limparArquivoTexto(arquivoRelatorio);
        UtilCRCArquivoTexto.printLnNoArquivo(mensagem, arquivoRelatorio);
    }

    @Override
    public void exibirRelatorioCompleto() {

        List<ItfInfoErroSB> erros = executarTestesBancoAcoes();
        exibirEGravarRelatorioDeErro(erros);
    }

    @Override
    public void exibirRelatorioAcoes() {
        List<ItfInfoErroSB> erros = executarTestesAcoes();
        exibirEGravarRelatorioDeErro(erros);
    }

    @Override
    public void exibirRelatorioBanco() {
        List<ItfInfoErroSB> erros = executarTestesBanco();
        exibirEGravarRelatorioDeErro(erros);
    }

}
