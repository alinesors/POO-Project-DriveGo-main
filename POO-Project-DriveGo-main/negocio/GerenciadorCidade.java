package negocio;

import excecoes.CidadeRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.Cidade;
import servico.CidadeServico;

/**
 * Classe responsável por gerenciar as operações relacionadas às cidades.
 * Esta classe atua como uma camada intermediária entre a interface de usuário e
 * o serviço de cidades, encapsulando a lógica de negócios.
 */
public class GerenciadorCidade {

    private final CidadeServico cidadeServico;

    public GerenciadorCidade(CidadeServico cidadeServico) {
        this.cidadeServico = cidadeServico;
    }

    public GerenciadorCidade() throws CidadeRepositorioException {
        this.cidadeServico = new CidadeServico();
    }

    /**
     * Método para cadastrar uma cidade
     * @param cidade A cidade a ser cadastrada
     * @throws DadosInvalidosException Se os dados da cidade forem inválidos
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void cadastrarCidade(Cidade cidade) throws DadosInvalidosException, PersistenciaException {
        if (cidade == null || cidade.getNome() == null || cidade.getNome().isEmpty()) {
            throw new DadosInvalidosException("Cidade ou nome inválido.");
        }
        cidadeServico.cadastrarCidade(cidade);
    }

    /**
     * Método para listar todas as cidades
     * @return Lista de todas as cidades cadastradas
     * @throws PersistenciaException Se ocorrer um erro na recuperação dos dados
     */
    public List<Cidade> listarCidades() throws PersistenciaException {
        return cidadeServico.listarCidades();
    }

    /**
     * Método para buscar uma cidade pelo nome
     * @param nome O nome da cidade a ser buscada
     * @return A cidade encontrada
     * @throws DadosInvalidosException Se o nome for nulo ou vazio
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na recuperação dos dados
     */
    public Cidade buscarCidade(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O id da cidade não pode ser nulo ou vazio.");
        }
        return cidadeServico.buscarCidade(id);
    }

    /**
     * Método para atualizar uma cidade
     * @param cidade A cidade com os dados atualizados
     * @throws DadosInvalidosException Se os dados da cidade forem inválidos
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void atualizarCidade(Cidade cidade) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (cidade == null || cidade.getNome() == null || cidade.getNome().isEmpty()) {
            throw new DadosInvalidosException("Cidade ou nome inválido para atualização.");
        }
        cidadeServico.atualizarCidade(cidade);
    }

    /**
     * Método para remover uma cidade
     * @param nome O nome da cidade a ser removida
     * @throws DadosInvalidosException Se o nome for nulo ou vazio
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void removerCidade(String nome) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (nome == null || nome.isEmpty()) {
            throw new DadosInvalidosException("O nome da cidade não pode ser nulo ou vazio.");
        }
        cidadeServico.removerCidade(nome);
    }

}