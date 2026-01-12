/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilCRCArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import java.io.File;
import org.jboss.forge.roaster.model.source.JavaSource;

/**
 *
 * @author desenvolvedor
 */
public abstract class GeradorGenerico {

    private String diretorioAlternativo = null;
    private final TIPO_PACOTE tipoPacote;

    public enum TIPO_PACOTE {
        IMPLEMENTACAO, TESTES
    }

    public abstract JavaSource getCodigoJava();

    public GeradorGenerico(String pDiretorioAlternativo) {
        diretorioAlternativo = pDiretorioAlternativo;
        tipoPacote = TIPO_PACOTE.IMPLEMENTACAO;
    }

    public GeradorGenerico(String pDiretorioAlternativo, TIPO_PACOTE pTipoPacote) {
        diretorioAlternativo = pDiretorioAlternativo;
        tipoPacote = pTipoPacote;
    }

    public String getCaminhoLocalBaseSalvarCodigo(TIPO_PACOTE pTipoPacote) {
        if (diretorioAlternativo != null) {
            return diretorioAlternativo;
        }
        switch (pTipoPacote) {
            case IMPLEMENTACAO:
                return SBCore.getCaminhoDesenvolvimento() + "/src/main/java/";
            case TESTES:
                return SBCore.getCaminhoDesenvolvimento() + "/src/test/java/";
            default:
                throw new AssertionError(pTipoPacote.name());
        }

    }

    public String getCaminhoLocalSalvarCodigo() {

        String caminhoClasse = getCodigoJava().getPackage();
        return getCaminhoLocalBaseSalvarCodigo(tipoPacote) + caminhoClasse.replace(".", "/") + "/" + getCodigoJava().getName() + ".java";
    }

    public void salvarEmDiretorioPadraoSubstituindoAnterior() {
        if (!SBCore.isEmModoDesenvolvimento()) {
            throw new UnsupportedOperationException("A criação de código só está deve ser utilizada no modo de desenvolvimento");
        }
        System.out.println("O código abaixo será Salvo em :" + getCaminhoLocalSalvarCodigo());
        System.out.println("-------------");
        System.out.println(getCodigoJava().toString());

        UtilCRCArquivoTexto.escreverEmArquivoSubstituindoArqAnterior(getCaminhoLocalSalvarCodigo(), getCodigoJava().toString());
    }

    public void salvarEmDiretorioPadraCASO_NAO_EXISTA() {
        File arquivo = new File(getCaminhoLocalSalvarCodigo());
        if (!arquivo.exists()) {
            UtilCRCArquivoTexto.escreverEmArquivoSubstituindoArqAnterior(getCaminhoLocalSalvarCodigo(), getCodigoJava().toString());
        }

    }

    public final void adicionarReferenciaDeEntidade(String entidadeNomeSimples) {
        Class classeEntidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(entidadeNomeSimples);
        getCodigoJava().addImport(classeEntidade);
        getCodigoJava().addAnnotation(InfoReferenciaEntidade.class).setLiteralValue("tipoObjeto ", classeEntidade.getSimpleName() + ".class");

    }
}
