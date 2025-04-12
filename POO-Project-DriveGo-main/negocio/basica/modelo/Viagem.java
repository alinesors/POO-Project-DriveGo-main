package negocio.basica.modelo;

import java.io.Serializable;
/**
 * A classe abstrata Viagem representa uma viagem genérica no sistema.
 * Ela serve como superclasse para todos os tipos de viagens, contendo os atributos e comportamentos comuns.
 * Implementa a interface Serializable para permitir a serialização de objetos.
 */
public abstract class Viagem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private final String id;
    private Motorista motorista;
    private Cliente cliente;
    private Origem origem;
    private Destino destino;
    private FormaDePagamento formaDePagamento;
    private String status;
    private double valorFinal;
    private String tipo;
    private Avaliacao avaliacaoMotorista;
    private Avaliacao avaliacaoCliente;

    // Construtor
    public Viagem(Motorista motorista, Cliente cliente, Origem origem, Destino destino, FormaDePagamento formaDePagamento, String tipo) {
        this.id = String.valueOf(idCounter++);
        this.motorista = motorista;
        this.cliente = cliente;
        this.origem = origem;
        this.destino = destino;
        this.formaDePagamento = formaDePagamento;
        this.status = "Solicitada";
        this.tipo = tipo;
    }

    //getters e setters
    public static void setIdCounter(int idCounter) {
        Viagem.idCounter = idCounter;
    }

    public String getId() {
        return id;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Avaliacao getAvaliacaoMotorista() {
        return avaliacaoMotorista;
    }

    public void setAvaliacaoMotorista(Avaliacao avaliacaoMotorista) {
        this.avaliacaoMotorista = avaliacaoMotorista;
    }

    public Avaliacao getAvaliacaoCliente() {
        return avaliacaoCliente;
    }

    public void setAvaliacaoCliente(Avaliacao avaliacaoCliente) {
        this.avaliacaoCliente = avaliacaoCliente;
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