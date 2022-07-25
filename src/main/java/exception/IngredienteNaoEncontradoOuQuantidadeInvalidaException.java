package exception;

public class IngredienteNaoEncontradoOuQuantidadeInvalidaException extends IllegalArgumentException {

    static final String mensagem = "_Ingrediente não encontrado ou quantidade inválida_";

    public IngredienteNaoEncontradoOuQuantidadeInvalidaException() {
        super(mensagem);
    }
}
