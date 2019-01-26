/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW;

import br.org.coletivojava.erp.comunicacao.transporte.ERPTransporteComunicacao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ArmazenamentoComunicacaoTransient;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.CentralComunicaoAbstrato;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ComunicacaoTransient;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfArmazenamentoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDestinatario;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfModeloMensagem;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfTipoTransporteComunicacao;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.ItensGenericos.basico.UsuarioAplicacaoEmExecucao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import javax.swing.JOptionPane;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItffabricaTrasporteComunicacao;

/**
 *
 * @author salvioF
 */
public class CentralComunicacaoDesktop extends CentralComunicaoAbstrato {

    private final ArmazenamentoComunicacaoTransient aramazenamento;

    public CentralComunicacaoDesktop() {
        aramazenamento = new ArmazenamentoComunicacaoTransient();
    }

    @Override
    public ItfComunicacao gerarComunicacaoEntre_Usuairos(FabTipoComunicacao tipocomunicacao,
            ItfUsuario pRemetente, ItfDestinatario pDestinatario,
            String mensagem,
            ItffabricaTrasporteComunicacao... tiposTransporte
    ) {

        try {
            ItfComunicacao comunicacao
                    = new ComunicacaoTransient(pRemetente, pDestinatario, tipocomunicacao.getRegistro(), gerarListaTransportes(tiposTransporte));

            comunicacao.setMensagem(mensagem);
            comunicacao.setNome(mensagem);
            if (getAramazenamento().registrarInicioComunicacao(comunicacao)) {
                return comunicacao;
            } else {
                return null;
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro gerando comunicação entre usuários", t);
            return null;
        }
    }

    @Override
    public ItfComunicacao iniciarComunicacaoEntre_Usuairos(FabTipoComunicacao tipocomunicacao, ItfUsuario pRemetente, ItfDestinatario pDestinatario, ItfModeloMensagem mensagem, ItffabricaTrasporteComunicacao... tiposTransporte) {
        ItfComunicacao comunicacao = new ComunicacaoTransient(pRemetente, pDestinatario, tipocomunicacao.getRegistro(), gerarListaTransportes(tiposTransporte));

        getAramazenamento().registrarInicioComunicacao(comunicacao);

        for (ItffabricaTrasporteComunicacao tipoTranposporte : tiposTransporte) {
            try {

                ItfDisparoComunicacao disparo = (ItfDisparoComunicacao) tipoTranposporte.getImplementacaoDoContexto();
                disparo.dispararInicioComunicacao(comunicacao);
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro enviando mensagem de comunicação", t);
            }
        }
        throw new UnsupportedOperationException("Processador de mensagem ainda não implementado;");

    }

    @Override
    public FabTipoRespostaComunicacao aguardarRespostaComunicacao(ItfTipoTransporteComunicacao pTransporte,
            ItfComunicacao pComunicacao, int tempoAguardar, FabTipoRespostaComunicacao pTipoRespostaTempoFinal) {
        FabTipoComunicacao tipocomunicacao = pComunicacao.getTipoComunicacao().getFabTipoComunicacao();

        int dialogResult
                = JOptionPane.showConfirmDialog(null, pComunicacao.getMensagem(),
                        "Deseja continuar?", JOptionPane.YES_OPTION);
        if (dialogResult
                == JOptionPane.YES_OPTION) {
            return FabTipoRespostaComunicacao.SIM;
        } else {
            System.out.println("não");
            return FabTipoRespostaComunicacao.NAO;
        }

    }

    @Override
    public ItfComunicacao gerarComunicacaoSistema_UsuairoLogado(FabTipoComunicacao tipocomunicacao, String mensagem, ItffabricaTrasporteComunicacao... tiposTransporte) {
        ItfComunicacao comunicacao
                = new ComunicacaoTransient(new UsuarioAplicacaoEmExecucao(), SBCore.getUsuarioLogado(),
                        tipocomunicacao.getRegistro(), gerarListaTransportes(tiposTransporte));
        comunicacao.setMensagem(mensagem);
        comunicacao.setNome(mensagem);
        if (getAramazenamento().registrarInicioComunicacao(comunicacao)) {

            return comunicacao;
        } else {
            return null;
        }
    }

    @Override
    public ItfArmazenamentoComunicacao getAramazenamento() {
        return aramazenamento;
    }

    @Override
    public ItffabricaTrasporteComunicacao getFabricaTransportePadrao() {
        return ERPTransporteComunicacao.INTRANET_MENU;
    }
}
