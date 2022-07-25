package armazemTest;

import armazem.Armazem;
import exception.IngredienteJaCadastradoException;
import exception.IngredienteNaoEncontradoException;
import exception.IngredienteNaoEncontradoOuQuantidadeInvalidaException;
import ingrediente.Base;
import ingrediente.Fruta;
import ingrediente.Ingrediente;
import ingrediente.TipoBase;
import ingrediente.TipoFruta;
import ingrediente.TipoTopping;
import ingrediente.Topping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArmazemTest {

    public Armazem armazem;

    @BeforeEach
    public void beforeEach() {
        armazem = new Armazem();
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.cadastrarIngredienteEmEstoque(iogurte);
    }

    @Test
    void cadastrarIngredienteEmEstoqueTest() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);

        Assertions.assertEquals(0, armazem.getEstoque().get(morango));
    }

    @Test
    void cadastrarIngredienteEmEstoqueTest_JaCadastrado() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        var exception = Assertions.assertThrows(IngredienteJaCadastradoException.class, () -> armazem.cadastrarIngredienteEmEstoque(iogurte), "Exception not found");

        Assertions.assertEquals("_Ingrediente já cadastrado_", exception.getMessage());
    }

    @Test
    void descadastrarIngredienteEmEstoqueTest() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.descadastrarIngredienteEmEstoque(iogurte);

        Assertions.assertFalse(armazem.consultarIngrediente(iogurte));
    }

    @Test
    void descadastrarIngredienteEmEstoqueTest_IngredienteNaoEncontrado() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.descadastrarIngredienteEmEstoque(aveia), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado_", exception.getMessage());
    }

    @Test
    void adicionarQuantidadeDoIngredienteEmEstoqueTest() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 3);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 5);

        Assertions.assertEquals(8, armazem.getEstoque().get(morango));
    }

    @Test
    void adicionarQuantidadeDoIngredienteEmEstoque_IngredienteNaoEncontrado() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class, () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(aveia, 0), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    void adicionarQuantidadeDoIngredienteEmEstoque_QuantidadeInvalida() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class, () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(aveia, -2), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    void reduzirQuantidadeDoIngredienteEmEstoque() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2);

        Assertions.assertEquals(3, armazem.getEstoque().get(morango));

    }

    @Test
    void reduzirQuantidadeDoIngredienteEmEstoque_IngredienteNaoEncontrado() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 0), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    void reduzirQuantidadeDoIngredienteEmEstoque_QuantidadeInvalida() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, -2), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    void consultarQuantidadeDoIngredienteEmEstoqueTest() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 3);
        var quantidadeEmEstoque = armazem.consultarQuantidadeDoIngredienteEmEstoque(morango);

        Assertions.assertEquals(3, quantidadeEmEstoque);

    }

    @Test
    void consultarQuantidadeDoIngredienteEmEstoque_IngredienteNaoExiste() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(morango), "Exception not found");

        Assertions.assertEquals("_Ingrediente não encontrado_", exception.getMessage());

    }
}
