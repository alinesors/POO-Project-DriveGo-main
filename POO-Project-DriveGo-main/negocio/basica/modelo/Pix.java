package negocio.basica.modelo;

//essa classe representa o pagamento via pix
//ela é uma subclasse de FormaDePagamento
public class Pix extends FormaDePagamento{

    private String chavePix;

    //esse construtor é usado para criar um objeto Pix
    public Pix(String chavePix) {
        super();
        this.chavePix = chavePix;
        this.atualizarTipo("Pix");
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public final void atualizarTipo(String tipo) {
        this.setTipo(tipo);
    }
    
}
