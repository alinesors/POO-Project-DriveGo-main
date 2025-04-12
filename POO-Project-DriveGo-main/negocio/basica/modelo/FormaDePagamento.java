package negocio.basica.modelo;

import java.io.Serializable;

//essa classe é abstrata
//ela é apenas uma superclasse para as classes CartaoCredito, CartaoDebito e Dinheiro
public abstract class FormaDePagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private final String id;
    private String clienteId; 
    private String tipo; 

    public FormaDePagamento() {
        this.id = String.valueOf(idCounter++);
    }

    public static void setIdCounter(int idCounter) {
        FormaDePagamento.idCounter = idCounter;
    }

    public String getId() {
        return id;
    }

    public String getClienteId() { 
        return clienteId;
    }

    public void setClienteId(String clienteId) { 
        this.clienteId = clienteId;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}