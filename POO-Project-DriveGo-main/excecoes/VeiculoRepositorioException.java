package excecoes;

public class VeiculoRepositorioException extends Exception {
    
    public VeiculoRepositorioException(String mensagem) {
        super(mensagem);
    }

    public VeiculoRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
