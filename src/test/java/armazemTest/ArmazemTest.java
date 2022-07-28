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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArmazemTest {

    public Armazem armazem;

    @BeforeEach
    public void Setup() {
        armazem = new Armazem();
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.cadastrarIngredienteEmEstoque(iogurte);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(iogurte, 3);
    }

    @Test
    @DisplayName("Deve verificar se o ingrediente está cadastrado no estoque e a quantidade existente.")
    void verificaIngredienteNoEstoque() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.getEstoque().get(iogurte);
        Assertions.assertTrue(armazem.consultarIngrediente(iogurte));
        Assertions.assertEquals(3, armazem.getEstoque().get(iogurte));
    }

    @Test
    @DisplayName("Deve retornar false se o ingrediente estiver cadastrado no estoque.")
    void verificaIngredienteNaoCadastradoNoEstoque() {
        Ingrediente sorvete = new Base(TipoBase.Sorvete);
        armazem.getEstoque().get(sorvete);
        Assertions.assertFalse(armazem.consultarIngrediente(sorvete));
    }

    @Test
    @DisplayName("Deve cadastrar novo ingrediente no estoque.")
    void cadastrarIngredienteEmEstoqueTest() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);

        Assertions.assertEquals(0, armazem.getEstoque().get(morango));
    }

    @Test
    @DisplayName("Deve retornar uma exception caso tente cadastrar novamente um ingrediente já cadastrado.")
    void cadastrarIngredienteEmEstoqueTestIngredienteJaCadastrado() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        var exception = Assertions.assertThrows(IngredienteJaCadastradoException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(iogurte));

        Assertions.assertEquals("_Ingrediente já cadastrado_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve descastrar o ingrediente do estoque.")
    void descadastrarIngredienteEmEstoqueTest() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.descadastrarIngredienteEmEstoque(iogurte);

        Assertions.assertFalse(armazem.consultarIngrediente(iogurte));
    }

    @Test
    @DisplayName("Deve retornar um exception caso tente descadastrar um ingrediente que não estava cadastrado.")
    void descadastrarIngredienteEmEstoqueTestIngredienteNaoEncontrado() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.descadastrarIngredienteEmEstoque(aveia));

        Assertions.assertEquals("_Ingrediente não encontrado_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve adicionar uma nova quantidade de ingredientes somando aos que já existiam em estoque.")
    void adicionarQuantidadeDoIngredienteEmEstoqueTest() {
        Ingrediente iogurte = new Base(TipoBase.Iogurte);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(iogurte, 5);

        Assertions.assertEquals(8, armazem.getEstoque().get(iogurte));
    }

    @Test
    @DisplayName("Deve retornar uma exception caso tente adicionar uma nova quantidade a um produto que não está cadastrado no estoque.")
    void adicionarQuantidadeDoIngredienteEmEstoqueIngredienteNaoEncontrado() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(aveia, 0));

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma exception caso a quantidade a ser adicionada seja inválida.")
    void adicionarQuantidadeDoIngredienteEmEstoqueQuantidadeInvalida() {
        Ingrediente aveia = new Topping(TipoTopping.Aveia);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(aveia, -2));
        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover do estoque a quantidade selecionada.")
    void reduzirQuantidadeDoIngredienteEmEstoque() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 2);

        Assertions.assertEquals(3, armazem.getEstoque().get(morango));

    }

    @Test
    @DisplayName("Deve retornar uma exception caso o ingrediente a ser removido não esteja cadastrado no estoque.")
    void reduzirQuantidadeDoIngredienteEmEstoqueIngredienteNaoEncontrado() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, 0));

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma exception caso a quantidade a ser reduzida seja inválida.")
    void reduzirQuantidadeDoIngredienteEmEstoqueQuantidadeInvalida() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoOuQuantidadeInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(morango, -2));

        Assertions.assertEquals("_Ingrediente não encontrado ou quantidade inválida_", exception.getMessage());
    }

    @Test
    @DisplayName("Deve consultar a quantidade do ingrediente cadastrado no estoque.")
    void consultarQuantidadeDoIngredienteEmEstoqueTest() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(morango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(morango, 3);
        var quantidadeEmEstoque = armazem.consultarQuantidadeDoIngredienteEmEstoque(morango);

        Assertions.assertEquals(3, quantidadeEmEstoque);

    }

    @Test
    @DisplayName("Deve retornar uma exception caso tente consultar a quantidade de um produto que não está cadastrado no estoque.")
    void consultarQuantidadeDoIngredienteEmEstoqueIngredienteNaoExiste() {
        Ingrediente morango = new Fruta(TipoFruta.Morango);
        var exception = Assertions.assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(morango));

        Assertions.assertEquals("_Ingrediente não encontrado_", exception.getMessage());

    }
}
