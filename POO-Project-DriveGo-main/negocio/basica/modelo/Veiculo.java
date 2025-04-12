package negocio.basica.modelo;

import java.io.Serializable;
/**
 * A classe abstrata Veiculo representa um veículo genérico no sistema.
 * Ela serve como superclasse para todos os tipos de veículos.
 * Implementa a interface Serializable para permitir a serialização de objetos.
 */
public abstract class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private final String id;
    private String modelo;
    private String marca;
    private String cor;
    private int anoFabricacao;
    private String placa;
    private Motorista motorista;
    //construtor da classe Veiculo
    public Veiculo(String modelo, String marca, String cor, int anoFabricacao, String placa) {
        this.id = String.valueOf(idCounter++);
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
    }
    //Define o contador de IDs estático.
    public static void setIdCounter(int idCounter) {
        Veiculo.idCounter = idCounter;
    }
    //Obtém o modelo do veículo.
    public String getModelo() {
        return modelo;
    }
    //Define o modelo do veículo.
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    //Obtém a marca do veículo.
    public String getMarca() {
        return marca;
    }
    //Define a marca do veículo.
    public void setMarca(String marca) {
        this.marca = marca;
    }
    //Obtém a cor do veículo.
    public String getCor() {
        return cor;
    }
    //Define a cor do veículo.
    public void setCor(String cor) {
        this.cor = cor;
    }
    //Obtém o ano de fabricação do veículo.
    public int getAnoFabricacao() {
        return anoFabricacao;
    }
    //Define o ano de fabricação do veículo.
    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
    //Obtém a placa do veículo.
    public String getPlaca() {
        return placa;
    }
    //Define a placa do veículo.
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    //Obtém o contador de IDs estático.
    public static int getIdCounter() {
        return idCounter;
    }
    //Obtém o ID do veículo.
    public String getId() {
        return id;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

}