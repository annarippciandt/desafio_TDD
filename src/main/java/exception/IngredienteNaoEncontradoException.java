package exception;

public class IngredienteNaoEncontradoException extends IllegalArgumentException {

    static final String mensagem = "_Ingrediente não encontrado_";

    public IngredienteNaoEncontradoException() {
        super(mensagem);
    }
}
