package dados.Interface;

import excecoes.PagamentoRepositorioException;
import java.util.List;
import negocio.basica.modelo.FormaDePagamento;

public interface IRepositorioPagamento {

    void salvarFormaDePagamento(FormaDePagamento entidade) throws PagamentoRepositorioException;

    FormaDePagamento buscarFormaDePagamento(String id) throws PagamentoRepositorioException;

    void atualizarFormaDePagamento(FormaDePagamento entidade) throws PagamentoRepositorioException;

    void removerFormaDePagamento(FormaDePagamento formaDePagamento) throws PagamentoRepositorioException;

    List<FormaDePagamento> listarFormasDePagamento() throws PagamentoRepositorioException;

    List<FormaDePagamento> listarPorCliente(String clienteId) throws PagamentoRepositorioException;

}
