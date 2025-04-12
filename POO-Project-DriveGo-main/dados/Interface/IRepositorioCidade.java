package dados.Interface;

import excecoes.CidadeRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cidade;

public interface IRepositorioCidade {

    void salvarCidade(Cidade cidade) throws CidadeRepositorioException; // Método para salvar uma cidade

    Cidade buscarCidade(String id) throws CidadeRepositorioException; // Método para buscar uma cidade pelo nome

    void atualizarCidade(Cidade cidade) throws CidadeRepositorioException; // Método para atualizar uma cidade

    void removerCidade(Cidade cidade) throws CidadeRepositorioException; // Método para remover uma cidade pelo nome

    List<Cidade> listarCidades() throws CidadeRepositorioException; // Método para listar todas as cidades

}
