package exception;

public class IngredienteJaCadastradoException extends IllegalArgumentException {
    static final String mensagem = "_Ingrediente já cadastrado_";

    public IngredienteJaCadastradoException() {
        super(mensagem);
    }
}
