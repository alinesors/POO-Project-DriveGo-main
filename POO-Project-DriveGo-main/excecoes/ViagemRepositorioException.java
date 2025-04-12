package excecoes;


public class ViagemRepositorioException extends Exception {

    public ViagemRepositorioException(String mensagem) {
        super(mensagem);
    }

    public ViagemRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
