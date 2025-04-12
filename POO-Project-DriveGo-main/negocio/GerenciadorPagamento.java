package negocio;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PagamentoRecusadoException;
import excecoes.PagamentoRepositorioException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.CartaoCredito;
import negocio.basica.modelo.FormaDePagamento;
import servico.PagamentoServico;

/**
 * Classe responsável por gerenciar as operações relacionadas a formas de pagamento.
 * Esta classe atua como uma camada intermediária entre a interface de usuário e os serviços de pagamento.
 */
public class GerenciadorPagamento {

    private final PagamentoServico pagamentoServico;

    public GerenciadorPagamento(PagamentoServico pagamentoServico) {
        this.pagamentoServico = pagamentoServico;
    }

    public GerenciadorPagamento() throws PagamentoRepositorioException {
        this.pagamentoServico = new PagamentoServico();
    }

    // Método para cadastrar uma nova forma de pagamento
    public void cadastrarFormaDePagamento(FormaDePagamento formaDePagamento)
            throws DadosInvalidosException, PersistenciaException {
        if (formaDePagamento == null) { // Verifica se a forma de pagamento é nulo
            throw new DadosInvalidosException("A forma de pagamento não pode ser nula.");
        }
        pagamentoServico.cadastrarFormaDePagamento(formaDePagamento); // Chama o serviço para cadastrar a forma de pagamento
    }

    // Método para listar todas as formas de pagamento
    public List<FormaDePagamento> listarFormasDePagamento() throws PersistenciaException {
        return pagamentoServico.listarFormasDePagamento(); // Chama o serviço para listar todas as formas de pagamento
    }
    // Método para buscar uma forma de pagamento por ID
    public FormaDePagamento buscarFormaDePagamentoPorId(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) { // Verifica se o ID é nulo ou vazio
            throw new DadosInvalidosException("O ID da forma de pagamento não pode ser nulo ou vazio.");
        }
        return pagamentoServico.buscarFormaDePagamento(id); // Chama o serviço para buscar a forma de pagamento pelo ID
    }

    // Método para atualizar uma forma de pagamento
    public void atualizarFormaDePagamento(FormaDePagamento formaDePagamento)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (formaDePagamento == null) { // Verifica se a forma de pagamento é nula
            throw new DadosInvalidosException("A forma de pagamento não pode ser nula.");
        }
        pagamentoServico.atualizarFormaDePagamento(formaDePagamento); // Chama o serviço para atualizar a forma de pagamento
    }

    // Método para remover uma forma de pagamento
    public void removerFormaDePagamento(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) { // Verifica se o ID é nulo ou vazio
            throw new DadosInvalidosException("O ID da forma de pagamento não pode ser nulo ou vazio.");
        }
        pagamentoServico.removerFormaDePagamento(id); // Chama o serviço para remover a forma de pagamento
    }

    // Método para listar formas de pagamento por cliente
    public List<FormaDePagamento> listarFormasDePagamentoPorCliente(String clienteId)
            throws DadosInvalidosException, PersistenciaException { // Verifica se o ID do cliente é nulo ou vazio
        if (clienteId == null || clienteId.isEmpty()) { // Verifica se o ID do cliente é nulo ou vazio
            throw new DadosInvalidosException("O ID do cliente não pode ser nulo ou vazio.");
        }
        return pagamentoServico.listarFormasDePagamentoPorCliente(clienteId); // Chama o serviço para listar formas de pagamento por cliente
    }

    public boolean  processarPagamento(CartaoCredito cartaoCredito, double saldo) // Processa o pagamento com cartão de crédito
            throws DadosInvalidosException, PersistenciaException, PagamentoRecusadoException {
        if (cartaoCredito == null) { // Verifica se o cartão de crédito é nulo
            throw new DadosInvalidosException("A forma de pagamento não pode ser nula.");
        }
        return pagamentoServico.processarPagamento(cartaoCredito, saldo); // Chama o serviço para processar o pagamento
    }
   
}
