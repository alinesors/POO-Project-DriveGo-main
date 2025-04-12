package servico;

import dados.Arquivos.RepositorioVeiculoArquivo;
import dados.Interface.IRepositorioVeiculo;
import excecoes.DadosInvalidosException;
import excecoes.VeiculoRepositorioException;
import java.util.List;
import negocio.basica.modelo.Veiculo;

//essa classe é responsável por gerenciar as operações relacionadas aos veículos
public class VeiculoServico {

    private final IRepositorioVeiculo repositorioVeiculo;

    public VeiculoServico(IRepositorioVeiculo repositorioVeiculo) {
        this.repositorioVeiculo = repositorioVeiculo;
    }

    public VeiculoServico() throws VeiculoRepositorioException {
        this.repositorioVeiculo = new RepositorioVeiculoArquivo();
    }

    //método para cadastrar um veículo
    public void cadastrarVeiculo(Veiculo veiculo) throws DadosInvalidosException, VeiculoRepositorioException {
        if (veiculo == null || veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
            throw new DadosInvalidosException("Veículo ou placa inválida.");
        }
        if (repositorioVeiculo.buscarVeiculo(veiculo.getPlaca()) != null) {
            throw new VeiculoRepositorioException("Já existe um veículo com essa placa.");
        }
        repositorioVeiculo.salvarVeiculo(veiculo);
    }
    //método para buscar um veículo pelo número da placa
    public Veiculo buscarVeiculo(String placa) throws DadosInvalidosException, VeiculoRepositorioException {
        if (placa == null || placa.isEmpty()) {
            throw new DadosInvalidosException("Placa inválida.");
        }
        Veiculo v = repositorioVeiculo.buscarVeiculo(placa); //busca o veículo no repositório
        if (v == null) {
            throw new VeiculoRepositorioException("Veículo com placa " + placa + " não encontrado.");
        }
        return v;
    }
    //método para atualizar os dados de um veículo
    public void atualizarVeiculo(Veiculo veiculo) throws DadosInvalidosException, VeiculoRepositorioException {
        if (veiculo == null || veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) { //verifica se o veículo é nulo ou se a placa é nula ou vazia
            throw new DadosInvalidosException("Veículo ou placa inválida.");
        }
        if (repositorioVeiculo.buscarVeiculo(veiculo.getPlaca()) == null) { //busca o veículo no repositório
            throw new VeiculoRepositorioException("Veículo com placa " + veiculo.getPlaca() + " não encontrado para atualização.");
        }
        repositorioVeiculo.atualizarVeiculo(veiculo);
    }
    //método para remover um veículo
    public void removerVeiculo(String placa) throws DadosInvalidosException, VeiculoRepositorioException { //verifica se a placa é nula ou vazia
        if (placa == null || placa.isEmpty()) { //verifica se a placa é nula ou vazia
            throw new DadosInvalidosException("Placa inválida.");
        }
        Veiculo veiculo = repositorioVeiculo.buscarVeiculo(placa);
        if (veiculo == null) {
            throw new VeiculoRepositorioException("Veículo com placa " + placa + " não encontrado para remoção.");
        }
        repositorioVeiculo.removerVeiculo(veiculo);
    }
    //método para listar todos os veículos
    public List<Veiculo> listarVeiculos() throws VeiculoRepositorioException {
        return repositorioVeiculo.listarVeiculos(); //lista todos os veículos do repositório
    }
    //método para listar veículos por tipo
    public List<Veiculo> listarVeiculosPorTipo(String tipo) throws DadosInvalidosException, VeiculoRepositorioException {
        if (tipo == null || tipo.isEmpty()) { //verifica se o tipo é nulo ou vazio
            throw new DadosInvalidosException("Tipo de veículo não pode ser vazio.");
        }
        return repositorioVeiculo.listarPorTipo(tipo);
    }
}
