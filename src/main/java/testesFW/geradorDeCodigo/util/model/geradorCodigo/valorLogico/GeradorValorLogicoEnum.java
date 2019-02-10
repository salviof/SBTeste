/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumEscopoModel;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValorLogicoEnum extends GeradorEnumEscopoModel {

    public GeradorValorLogicoEnum(EstruturaDeEntidade entidade) {
        super(entidade.getNomeEntidade(), "ValoresLogicos" + entidade.getNomeEntidade());

        System.out.println(entidade.getCamposComValidadorLogico());
        adicionarReferenciaDeEntidade(entidade.getNomeEntidade());

        entidade.getCamposComValorLogico().forEach(cp
                -> {
            try {
                System.out.println("ValorLogico" + cp.getNomeDeclarado());

                getCodigoJava().addEnumConstant(cp.getNomeDeclarado().toUpperCase());
            } catch (Throwable t) {
                SBCore.RelatarErroAoUsuario(FabErro.SOLICITAR_REPARO, "Erro adicionando enum de validacao", t);
            }
        });

    }

}
