package excecoes;


public class AvaliacaoRepositorioException extends Exception {
    
    public AvaliacaoRepositorioException(String mensagem) {
        super(mensagem);
    }

    public AvaliacaoRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
