package servico;

import dados.Arquivos.RepositorioCidadeArquivo;
import dados.Interface.IRepositorioCidade;
import excecoes.CidadeRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.Cidade;

//essa classe é responsável por gerenciar as operações relacionadas às cidades
public class CidadeServico {

    private final IRepositorioCidade repositorioCidade;

    public CidadeServico(IRepositorioCidade repositorioCidade) {
        this.repositorioCidade = repositorioCidade;
    }

    public CidadeServico() throws CidadeRepositorioException {
        this.repositorioCidade = new RepositorioCidadeArquivo();
    }
    //método para cadastrar uma nova cidade
    public void cadastrarCidade(Cidade cidade) throws DadosInvalidosException, PersistenciaException {
        if (cidade == null || cidade.getNome() == null || cidade.getNome().isEmpty()) { //essa linha verifica se a cidade é nula ou se o nome da cidade é nulo ou vazio
            throw new DadosInvalidosException("O nome da cidade não pode ser nulo ou vazio.");
        }

        try {
            if (repositorioCidade.buscarCidade(cidade.getNome()) != null) { //essa linha verifica se a cidade já existe no repositório
                throw new DadosInvalidosException("Cidade já cadastrada com esse nome.");
            }

            repositorioCidade.salvarCidade(cidade); //essa linha salva a cidade no repositório
        } catch (CidadeRepositorioException e) { //essa linha captura a exceção caso ocorra um erro ao salvar a cidade
            throw new PersistenciaException("Erro ao salvar cidade.", e);
        }
    }

    public List<Cidade> listarCidades() throws PersistenciaException { //essa linha lista todas as cidades cadastradas
        try {
            return repositorioCidade.listarCidades(); //essa linha retorna a lista de cidades
        } catch (CidadeRepositorioException e) {    //essa linha captura a exceção caso ocorra um erro ao listar as cidades
            throw new PersistenciaException("Erro ao listar cidades.", e);
        }
    }

    public Cidade buscarCidade(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException { //essa linha busca uma cidade pelo id
        if (id == null || id.isEmpty()) { //essa linha verifica se o id da cidade é nulo ou vazio
            throw new DadosInvalidosException("O id da cidade não pode ser nulo ou vazio.");
        }

        try {
            Cidade cidade = repositorioCidade.buscarCidade(id);
            if (cidade == null) {
                throw new EntidadeNaoEncontradaException("Cidade com id '" + id + "' não encontrada.");
            }
            return cidade;
        } catch (CidadeRepositorioException e) {
            throw new PersistenciaException("Erro ao buscar cidade.", e);
        }
    }

    //método para atualizar uma cidade existente
    //esse método recebe um objeto cidade e verifica se ele é válido
    public void atualizarCidade(Cidade cidade) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (cidade == null || cidade.getId() == null || cidade.getId().isEmpty()) {
            throw new DadosInvalidosException("A cidade é inválida para atualização.");
        }

        try {
            if (repositorioCidade.buscarCidade(cidade.getId()) == null) {
                throw new EntidadeNaoEncontradaException("Cidade com nome '" + cidade.getNome() + "' não encontrada para atualização.");
            }

            repositorioCidade.atualizarCidade(cidade);
        } catch (CidadeRepositorioException e) { //essa linha captura a exceção caso ocorra um erro ao atualizar a cidade
            throw new PersistenciaException("Erro ao atualizar cidade.", e);
        }
    }
    //método para remover uma cidade existente
    public void removerCidade(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O id da cidade para remoção não pode ser nulo ou vazio.");
        }

        try {
            Cidade cidade = repositorioCidade.buscarCidade(id); //essa linha busca a cidade pelo id
            if (cidade == null) {
                throw new EntidadeNaoEncontradaException("Cidade com id '" + id + "' não encontrada para remoção.");
            }

            repositorioCidade.removerCidade(cidade); //essa linha remove a cidade do repositório
        } catch (CidadeRepositorioException e) {
            throw new PersistenciaException("Erro ao remover cidade.", e);
        }
    }
}
