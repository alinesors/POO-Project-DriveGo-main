package negocio.basica.modelo;

import excecoes.DadosInvalidosException;

// CartaoCredito.java
// Classe que representa um cartão de crédito
// Extende a classe FormaDePagamento
public class CartaoCredito extends FormaDePagamento {

    private double limite;
    private double gasto;

    // Construtor da classe CartaoCredito
    public CartaoCredito(double limite) throws DadosInvalidosException {
        super();
        if (limite <= 0) {
            throw new DadosInvalidosException("O limite do cartão deve ser maior que zero.");
        }
        this.limite = limite;
        gasto = 0;
        this.atualizarTipo("Cartao de credito");
    }

    public double getLimite() {
        return limite;
    }

    // Método para definir o limite do cartão
    public void setLimite(double limite) throws DadosInvalidosException {
        if (limite <= 0) {
            throw new DadosInvalidosException("O limite do cartão deve ser maior que zero.");
        }
        this.limite = limite;
    }

    public double getGasto() {
        return gasto;
    }
    
    public void setGasto(double gasto) {
        this.gasto = gasto;
    }
    
    public boolean podePagar(double valor) {
        return (gasto + valor) <= limite;
    }

    public void atualizarGasto(double valor) {
        gasto += valor;
    }

    public final void atualizarTipo(String tipo) {
        this.setTipo(tipo);
    }
}
