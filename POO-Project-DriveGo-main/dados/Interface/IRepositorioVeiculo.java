package dados.Interface;

import excecoes.VeiculoRepositorioException;
import java.util.List;
import negocio.basica.modelo.Veiculo;

public interface IRepositorioVeiculo {

    void salvarVeiculo(Veiculo entidade) throws VeiculoRepositorioException;
    
    Veiculo buscarVeiculo(String id) throws VeiculoRepositorioException;

    void atualizarVeiculo(Veiculo entidade) throws VeiculoRepositorioException;

    void removerVeiculo(Veiculo veiculo) throws VeiculoRepositorioException;

    List<Veiculo> listarVeiculos() throws VeiculoRepositorioException;

    List<Veiculo> listarPorTipo(String tipo) throws VeiculoRepositorioException;

    

}
