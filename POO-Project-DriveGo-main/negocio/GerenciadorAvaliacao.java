package negocio;

import excecoes.AvaliacaoRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.Avaliacao;
import servico.AvaliacaoServico;

//esse classe é responsável por gerenciar as operações relacionadas às avaliações, como cadastrar, listar, buscar, atualizar e remover avaliações. Ela utiliza a classe AvaliacaoServico para realizar essas operações.
public class GerenciadorAvaliacao {

    private final AvaliacaoServico avaliacaoServico;

    public GerenciadorAvaliacao(AvaliacaoServico avaliacaoServico) {
        this.avaliacaoServico = avaliacaoServico;
    }

    public GerenciadorAvaliacao() throws AvaliacaoRepositorioException {
        this.avaliacaoServico = new AvaliacaoServico();
    }

    //método para cadastrar uma nova avaliação. Ele recebe um objeto Avaliacao como parâmetro e chama o método cadastrarAvaliacao da classe AvaliacaoServico.
    public void cadastrarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, PersistenciaException {
        avaliacaoServico.cadastrarAvaliacao(avaliacao);
    }
    //método para listar todas as avaliações. Ele chama o método listarAvaliacoes da classe AvaliacaoServico e retorna uma lista de objetos Avaliacao.
    public List<Avaliacao> listarAvaliacoes() throws PersistenciaException {
        return avaliacaoServico.listarAvaliacoes();
    }
    //método para buscar uma avaliação específica. Ele recebe um ID como parâmetro e chama o método buscarAvaliacao da classe AvaliacaoServico, retornando o objeto Avaliacao correspondente.
    public Avaliacao buscarAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return avaliacaoServico.buscarAvaliacao(id);
    }
    //método para atualizar uma avaliação existente. Ele recebe um objeto Avaliacao como parâmetro e chama o método atualizarAvaliacao da classe AvaliacaoServico.
    public void atualizarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        avaliacaoServico.atualizarAvaliacao(avaliacao);
    }
    //método para remover uma avaliação. Ele recebe um ID como parâmetro e chama o método removerAvaliacao da classe AvaliacaoServico.
    public void removerAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        avaliacaoServico.removerAvaliacao(id);
    }

    public double atualizarNotaMedia(String id) throws AvaliacaoRepositorioException, PersistenciaException {   
        double soma = 0;
        List<Avaliacao> avaliacoes = avaliacaoServico.listarAvaliacoes();
        for (Avaliacao n : avaliacoes) {
            soma += n.getNota();
        }
        return (avaliacoes.isEmpty()) ? 0 : soma / avaliacoes.size();
    }
}