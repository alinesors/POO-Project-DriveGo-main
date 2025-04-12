package negocio;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.VeiculoRepositorioException;
import java.util.List;
import negocio.basica.modelo.Veiculo;
import servico.VeiculoServico;

/**
 * Classe responsável por gerenciar as operações relacionadas aos veículos.
 * Esta classe atua como uma camada intermediária entre a interface de usuário e
 * o serviço de veículos, encapsulando a lógica de negócios.
 */
public class GerenciadorVeiculos {

    private final VeiculoServico veiculoServico;

    public GerenciadorVeiculos(VeiculoServico veiculoServico) {
        this.veiculoServico = veiculoServico;
    }

    public GerenciadorVeiculos() throws VeiculoRepositorioException {
        this.veiculoServico = new VeiculoServico();
    }

    /// Método para cadastrar um veículo
    public void cadastrarVeiculo(Veiculo veiculo) throws DadosInvalidosException, VeiculoRepositorioException {
        if (veiculo == null || veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
            throw new DadosInvalidosException("Veículo ou placa inválida.");
        }
        veiculoServico.cadastrarVeiculo(veiculo); // Cadastra o veículo no repositório
    }

    /// Método para listar todos os veículos
    public List<Veiculo> listarVeiculos() throws VeiculoRepositorioException {
        return veiculoServico.listarVeiculos(); // Retorna a lista de veículos cadastrados
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) throws DadosInvalidosException, EntidadeNaoEncontradaException, VeiculoRepositorioException {
        if (placa == null || placa.isEmpty()) { // Verifica se a placa é nula ou vazia
            throw new DadosInvalidosException("A placa não pode ser nula ou vazia.");
        }
        Veiculo veiculo = veiculoServico.buscarVeiculo(placa); // Busca o veículo pelo número da placa
        if (veiculo == null) { // Se o veículo não for encontrado, lança uma exceção
            throw new EntidadeNaoEncontradaException("Veículo com placa " + placa + " não encontrado.");
        }
        return veiculo;
    }

    /// Método para atualizar um veículo
    public void atualizarVeiculo(Veiculo veiculo) throws DadosInvalidosException, EntidadeNaoEncontradaException, VeiculoRepositorioException {
        if (veiculo == null || veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) { // Verifica se o veículo é nulo ou se a placa é nula ou vazia
            throw new DadosInvalidosException("Veículo ou placa inválida para atualização.");
        }
        veiculoServico.atualizarVeiculo(veiculo); // Atualiza o veículo no repositório
    }

    /// Método para remover um veículo
    public void removerVeiculo(String placa) throws DadosInvalidosException, EntidadeNaoEncontradaException, VeiculoRepositorioException {
        if (placa == null || placa.isEmpty()) { // Verifica se a placa é nula ou vazia
            throw new DadosInvalidosException("A placa do veículo não pode ser nula ou vazia.");
        }
        veiculoServico.removerVeiculo(placa);
    }

    /// Método para listar veículos por tipo
    public List<Veiculo> listarVeiculosPorTipo(String tipo) throws DadosInvalidosException, VeiculoRepositorioException {
        if (tipo == null || tipo.isEmpty()) { // Verifica se o tipo é nulo ou vazio
            throw new DadosInvalidosException("O tipo de veículo não pode ser nulo ou vazio.");
        }
        return veiculoServico.listarVeiculosPorTipo(tipo); // Retorna a lista de veículos do tipo especificado
    }
}
