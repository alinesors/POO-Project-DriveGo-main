package servico;

import dados.Arquivos.RepositorioAvaliacaoArquivo;
import dados.Interface.IRepositorioAvaliacao;
import excecoes.AvaliacaoRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.Avaliacao;

// esse código é parte de um sistema de gerenciamento de avaliações
public class AvaliacaoServico {

    private final IRepositorioAvaliacao repositorioAvaliacao;

    public AvaliacaoServico(IRepositorioAvaliacao repositorioAvaliacao) {
        this.repositorioAvaliacao = repositorioAvaliacao;
    }

    public AvaliacaoServico() throws AvaliacaoRepositorioException {
        this.repositorioAvaliacao = new RepositorioAvaliacaoArquivo();
    }

    // Método para cadastrar uma nova avaliação
    // Esse método verifica se a avaliação é válida e se a nota está dentro do intervalo permitido
    public void cadastrarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, PersistenciaException {
        if (avaliacao == null) {
            throw new DadosInvalidosException("A avaliação não pode ser nula.");
        }
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) { // Verifica se a nota está entre 0 e 10
            throw new DadosInvalidosException("A nota da avaliação deve estar entre 0 e 10.");
        }

        try {
            repositorioAvaliacao.inserirAvaliacao(avaliacao); // Insere a avaliação no repositório
        } catch (AvaliacaoRepositorioException e) {
            throw new PersistenciaException("Erro ao inserir avaliação no repositório", e);
        }
    }
    // Método para listar todas as avaliações
    // Esse método retorna uma lista de todas as avaliações cadastradas
    // Caso ocorra algum erro ao acessar o repositório, uma exceção de persistência é lançada
    public List<Avaliacao> listarAvaliacoes() throws PersistenciaException {
        try {
            return repositorioAvaliacao.listarAvaliacoes();
        } catch (AvaliacaoRepositorioException e) {
            throw new PersistenciaException("Erro ao listar avaliações", e);
        }
    }
    // Método para buscar uma avaliação específica pelo ID
    // Esse método verifica se o ID é válido e se a avaliação existe no repositório
    public Avaliacao buscarAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O ID da avaliação não pode ser nulo ou vazio.");
        }

        try {
            Avaliacao avaliacao = repositorioAvaliacao.buscarAvaliacao(id); // Busca a avaliação pelo ID
            if (avaliacao == null) {
                throw new EntidadeNaoEncontradaException("Avaliação com ID " + id + " não encontrada.");
            }
            return avaliacao;
        } catch (AvaliacaoRepositorioException e) {
            throw new PersistenciaException("Erro ao buscar avaliação", e);
        }
    }
    // Método para atualizar uma avaliação existente
    // Esse método verifica se a avaliação é válida e se a avaliação existe no repositório
    public void atualizarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (avaliacao == null) {
            throw new DadosInvalidosException("A avaliação não pode ser nula.");
        }

        try {
            if (repositorioAvaliacao.buscarAvaliacao(avaliacao.getId()) == null) {
                throw new EntidadeNaoEncontradaException("Avaliação com ID " + avaliacao.getId() + " não encontrada.");
            }
            repositorioAvaliacao.atualizarAvaliacao(avaliacao);
        } catch (AvaliacaoRepositorioException e) {
            throw new PersistenciaException("Erro ao atualizar avaliação", e);
        }
    }
    // Método para remover uma avaliação existente
    // Esse método verifica se o ID é válido e se a avaliação existe no repositório
    public void removerAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O ID da avaliação não pode ser nulo ou vazio.");
        }

        try {
            Avaliacao avaliacao = repositorioAvaliacao.buscarAvaliacao(id);
            if (avaliacao == null) {
                throw new EntidadeNaoEncontradaException("Avaliação com ID " + id + " não encontrada.");
            }
            repositorioAvaliacao.removerAvaliacao(avaliacao);
        } catch (AvaliacaoRepositorioException e) {
            throw new PersistenciaException("Erro ao remover avaliação", e);
        }
    }
}
