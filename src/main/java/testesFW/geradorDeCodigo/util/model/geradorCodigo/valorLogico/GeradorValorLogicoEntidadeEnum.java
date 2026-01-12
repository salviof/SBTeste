/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.valorLogico;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumPacoteApiEntidade;

/**
 *
 * @author desenvolvedor
 */
public class GeradorValorLogicoEntidadeEnum extends GeradorEnumPacoteApiEntidade {

    public GeradorValorLogicoEntidadeEnum(ItfEstruturaDeEntidade pEntidade) {
        super(pEntidade, "ValoresLogicos" + pEntidade.getNomeEntidade(), pEntidade.isUmaEntidadeModuloERP());

        System.out.println(pEntidade.getCamposComValidadorLogico());
        adicionarReferenciaDeEntidade(pEntidade.getNomeEntidade());

        pEntidade.getCamposComValorLogico().forEach(cp
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
