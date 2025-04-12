package negocio.basica.modelo;

//essa classe representa um veículo econômico
public class Economico extends Veiculo{
    //construtor da classe
    public Economico(String modelo, String marca, String cor, int anoFabricacao, String placa) {
        super("Econômico", marca, cor, anoFabricacao, placa);
    }   
}
