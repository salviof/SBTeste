/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import java.io.File;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumPacoteApiEntidade;
import java.lang.reflect.Field;

/**
 *
 * @author desenvolvedor
 */
public class GeradorReferenciaCampos extends GeradorEnumPacoteApiEntidade {

    public GeradorReferenciaCampos(ItfEstruturaDeEntidade pEstruturaEntidade, Class<? extends ComoEntidadeSimples> pEntidade, boolean pModuloERP) {
        super(pEstruturaEntidade, "CP" + pEstruturaEntidade.getNomeEntidade(), pModuloERP);
        adicionarReferenciaDeEntidade(pEstruturaEntidade.getNomeEntidade());

        for (Field campo : pEntidade.getDeclaredFields()) {
            getCodigoJava().addEnumConstant("_" + campo.getName().toUpperCase());
            getCodigoJava().addField().setPublic().setType(String.class).setStatic(true).setFinal(true).
                    setName(campo.getName().toLowerCase()).setLiteralInitializer("\"" + campo.getName() + "\"");
        }

    }

    @Override
    public String getCaminhoLocalBaseSalvarCodigo(TIPO_PACOTE pTipoPacote) {

        return super.getCaminhoLocalBaseSalvarCodigo(pTipoPacote); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
