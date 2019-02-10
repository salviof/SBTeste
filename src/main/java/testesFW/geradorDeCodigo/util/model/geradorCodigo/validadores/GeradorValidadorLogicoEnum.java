/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.validadores;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumEscopoModel;
import org.jboss.forge.roaster.model.source.AnnotationSource;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValidadorLogicoEnum extends GeradorEnumEscopoModel {

    public GeradorValidadorLogicoEnum(EstruturaDeEntidade entidade) {
        super(entidade.getNomeEntidade(), "Validadores" + entidade.getNomeEntidade());

        adicionarReferenciaDeEntidade(entidade.getNomeEntidade());
        entidade.getCamposComValidadorLogico().forEach(cp
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
