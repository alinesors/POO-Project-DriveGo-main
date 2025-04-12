package negocio.basica.modelo;
//essa classe representa uma viagem de passageiro, que herda da classe Viagem
public class ViagemPassageiro extends Viagem {

    public ViagemPassageiro(Motorista motorista, Cliente cliente, Origem origem, Destino destino, FormaDePagamento formaDePagamento) {
        super(motorista, cliente, origem, destino, formaDePagamento, "Passageiro");
    }
}
