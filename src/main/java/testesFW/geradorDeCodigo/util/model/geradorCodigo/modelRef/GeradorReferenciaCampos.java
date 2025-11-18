/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.modelRef;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import java.io.File;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumEscopoModel;
import java.lang.reflect.Field;

/**
 *
 * @author desenvolvedor
 */
public class GeradorReferenciaCampos extends GeradorEnumEscopoModel {

    public GeradorReferenciaCampos(Class<? extends ComoEntidadeSimples> entidade) {
        super(entidade.getSimpleName(), "CP" + entidade.getSimpleName());
        adicionarReferenciaDeEntidade(entidade.getSimpleName());
        for (Field campo : entidade.getDeclaredFields()) {
            getCodigoJava().addEnumConstant("_" + campo.getName().toUpperCase());
            getCodigoJava().addField().setPublic().setType(String.class).setStatic(true).setFinal(true).setName(campo.getName().toLowerCase()).setLiteralInitializer("\"" + campo.getName() + "\"");
        }

    }

    @Override
    public String getCaminhoLocalBaseSalvarCodigo(TIPO_PACOTE pTipoPacote) {
        if (false) {
            String caminhoCodigo = SBCore.getCaminhoDesenvolvimento();
            if (caminhoCodigo.endsWith("modelRegras")) {
                String caminhoV2 = caminhoCodigo.replace("modelRegras", "apiJava");
                if (new File(caminhoV2).isDirectory()) {
                    caminhoCodigo = caminhoV2;
                }
            }

            switch (pTipoPacote) {
                case IMPLEMENTACAO:
                    return caminhoCodigo + "/src/main/java/";

                case TESTES:
                    return caminhoCodigo + "/src/test/java/";
                default:
                    throw new AssertionError(pTipoPacote.name());

            }
        }
        return super.getCaminhoLocalBaseSalvarCodigo(pTipoPacote); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
