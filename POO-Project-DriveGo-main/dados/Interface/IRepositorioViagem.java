package dados.Interface;

import excecoes.ViagemRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Viagem;

public interface IRepositorioViagem {

    void salvarViagem(Viagem entidade) throws ViagemRepositorioException;

    Viagem buscarViagem(String id) throws ViagemRepositorioException;

    void atualizarViagem(Viagem entidade) throws ViagemRepositorioException;

    void removerViagem(Viagem viagem) throws ViagemRepositorioException;

    List<Viagem> listarViagens() throws ViagemRepositorioException;

    List<Viagem> listarPorCliente(Cliente cliente) throws ViagemRepositorioException;
    
    List<Viagem> listarPorMotorista(Motorista motorista) throws ViagemRepositorioException;

}

