package negocio.basica.modelo;

//essa classe representa o pagamento em dinheiro
public class Dinheiro extends FormaDePagamento{

    public Dinheiro() {
        super();
        this.atualizarTipo("Dinheiro");
    }

    public final void atualizarTipo(String tipo) {
        this.setTipo(tipo);
    }
}
