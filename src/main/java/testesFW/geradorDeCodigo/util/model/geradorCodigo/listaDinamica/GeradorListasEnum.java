/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.listaDinamica;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumEscopoModel;
import org.jboss.forge.roaster.model.source.AnnotationSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorListasEnum extends GeradorEnumEscopoModel {

    public GeradorListasEnum(EstruturaDeEntidade entidade) {
        super(entidade.getNomeEntidade(), "Listas" + entidade.getNomeEntidade());
        adicionarReferenciaDeEntidade(entidade.getNomeEntidade());
        entidade.getCamposComListaDinamica().forEach(cp
                -> {
            try {
                System.out.println("ValidadorLogico" + cp.getNomeDeclarado());
                getCodigoJava().addEnumConstant(cp.getNomeDeclarado().toUpperCase());
            } catch (Throwable t) {
                SBCore.RelatarErroAoUsuario(FabErro.SOLICITAR_REPARO, "Erro adicionando enum de validacao", t);
            }
        });

    }

}
