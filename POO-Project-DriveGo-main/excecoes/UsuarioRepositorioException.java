package excecoes;


public class UsuarioRepositorioException extends Exception {
    public UsuarioRepositorioException(String mensagem) {
        super(mensagem);
    }

    public UsuarioRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
