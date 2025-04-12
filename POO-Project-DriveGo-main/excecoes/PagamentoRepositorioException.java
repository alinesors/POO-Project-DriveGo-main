package excecoes;


public class PagamentoRepositorioException extends Exception {
    public PagamentoRepositorioException(String mensagem) {
        super(mensagem);
    }

    public PagamentoRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
