/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testesFW.geradorDeCodigo.util.model.geradorCodigo.entidadeEstatica;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaDeEntidade;
import org.coletivojava.fw.utilCoreBase.UtilCRCFabrica;
import testesFW.geradorDeCodigo.util.model.geradorCodigo.GeradorEnumPacoteApiEntidade;

/**
 *
 * @author salvio
 */
public class GeradorReferenciaEntidadeEstatica extends GeradorEnumPacoteApiEntidade {

    /**
     *
     * @param pFabrica
     */
    public GeradorReferenciaEntidadeEstatica(Class<? extends ComoFabrica> pFabrica) {
        this(MapaObjetosProjetoAtual.getEstruturaObjeto(UtilCRCFabrica.getClasseEntidadePorEnum(pFabrica.getEnumConstants()[0])), pFabrica.getSimpleName().replace("Fab", "") + "Identificador");
        EstruturaDeEntidade estrutira = MapaObjetosProjetoAtual.getEstruturaObjeto(UtilCRCFabrica.getClasseEntidadePorEnum(pFabrica.getEnumConstants()[0]));
        adicionarReferenciaDeEntidade(estrutira.getNomeEntidade());

        for (ComoFabrica enumEntidadeEstatica : pFabrica.getEnumConstants()) {
            getCodigoJava().addField().setPublic().setType(String.class).setStatic(true).setFinal(true).setName(enumEntidadeEstatica.toString().toLowerCase()).setLiteralInitializer("\""
                    + enumEntidadeEstatica.getIdentificadorUnico() + "\"");
        }
    }

    public GeradorReferenciaEntidadeEstatica(ItfEstruturaDeEntidade pEstruturaEntidade, String pNomeClasse) {
        super(pEstruturaEntidade, pNomeClasse, false);

    }

}
