package excecoes;


public class CidadeRepositorioException extends Exception {
    public CidadeRepositorioException(String mensagem) {
        super(mensagem);
    }

    public CidadeRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
