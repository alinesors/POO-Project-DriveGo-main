package dados.Interface;

import excecoes.AvaliacaoRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.Avaliacao;

public interface IRepositorioAvaliacao {

    void inserirAvaliacao(Avaliacao avaliacao) throws PersistenciaException, DadosInvalidosException, AvaliacaoRepositorioException;

    List<Avaliacao> listarAvaliacoes() throws PersistenciaException, AvaliacaoRepositorioException;

    Avaliacao buscarAvaliacao(String id) throws EntidadeNaoEncontradaException, PersistenciaException, DadosInvalidosException, AvaliacaoRepositorioException;

    void atualizarAvaliacao(Avaliacao avaliacao) throws EntidadeNaoEncontradaException, PersistenciaException, DadosInvalidosException, AvaliacaoRepositorioException;

    void removerAvaliacao(Avaliacao avaliacao) throws EntidadeNaoEncontradaException, PersistenciaException, DadosInvalidosException, AvaliacaoRepositorioException;


}
