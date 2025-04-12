package servico;

import dados.Arquivos.RepositorioPagamentoArquivo;
import dados.Interface.IRepositorioPagamento;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PagamentoRecusadoException;
import excecoes.PagamentoRepositorioException;
import excecoes.PersistenciaException;
import java.util.List;
import negocio.basica.modelo.CartaoCredito;
import negocio.basica.modelo.FormaDePagamento;

/**
 * Classe responsável por gerenciar as operações relacionadas a formas de pagamento.
 */
public class PagamentoServico {

    private final IRepositorioPagamento repositorioPagamento;

    public PagamentoServico(IRepositorioPagamento repositorioPagamento) {
        this.repositorioPagamento = repositorioPagamento;
    }

    public PagamentoServico() throws PagamentoRepositorioException {
        this.repositorioPagamento = new RepositorioPagamentoArquivo();
    }

    // Método para cadastrar uma nova forma de pagamento
    public void cadastrarFormaDePagamento(FormaDePagamento formaDePagamento) throws DadosInvalidosException, PersistenciaException {
        if (formaDePagamento == null) {
            throw new DadosInvalidosException("Forma de pagamento inválida. O valor deve ser maior que zero.");
        }

        try {
            List<FormaDePagamento> formas = repositorioPagamento.listarFormasDePagamento();
            for (FormaDePagamento f : formas) {
                if (f.getId().equals(formaDePagamento.getId())) {
                    throw new DadosInvalidosException("Já existe uma forma de pagamento com esse ID.");
                }
            }
            repositorioPagamento.salvarFormaDePagamento(formaDePagamento);
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao cadastrar forma de pagamento.", e);
        }
    }

    // Método para listar todas as formas de pagamento
    public List<FormaDePagamento> listarFormasDePagamento() throws PersistenciaException {
        try {
            return repositorioPagamento.listarFormasDePagamento();
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao listar formas de pagamento.", e);
        }
    }

    // Método para buscar uma forma de pagamento pelo ID
    public FormaDePagamento buscarFormaDePagamento(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O ID da forma de pagamento não pode ser nulo ou vazio.");
        }

        try {
            FormaDePagamento pagamento = repositorioPagamento.buscarFormaDePagamento(id);
            if (pagamento == null) {
                throw new EntidadeNaoEncontradaException("Forma de pagamento com ID " + id + " não encontrada.");
            }
            return pagamento;
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao buscar forma de pagamento.", e);
        }
    }

    // Método para atualizar uma forma de pagamento
    public void atualizarFormaDePagamento(FormaDePagamento formaDePagamento) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (formaDePagamento == null) {
            throw new DadosInvalidosException("Forma de pagamento inválida para atualização.");
        }

        try {
            FormaDePagamento existente = repositorioPagamento.buscarFormaDePagamento(formaDePagamento.getId());
            if (existente == null) {
                throw new EntidadeNaoEncontradaException("Forma de pagamento com ID " + formaDePagamento.getId() + " não encontrada.");
            }

            repositorioPagamento.atualizarFormaDePagamento(formaDePagamento);
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao atualizar forma de pagamento.", e);
        }
    }

    // Método para remover uma forma de pagamento
    public void removerFormaDePagamento(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("O ID da forma de pagamento não pode ser nulo ou vazio.");
        }

        try {
            FormaDePagamento forma = repositorioPagamento.buscarFormaDePagamento(id);
            if (forma == null) {
                throw new EntidadeNaoEncontradaException("Forma de pagamento com ID " + id + " não encontrada para remoção.");
            }

            repositorioPagamento.removerFormaDePagamento(forma);
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao remover forma de pagamento.", e);
        }
    }

    // Método para listar formas de pagamento por cliente
    public List<FormaDePagamento> listarFormasDePagamentoPorCliente(String clienteId) throws DadosInvalidosException, PersistenciaException {
        if (clienteId == null || clienteId.isEmpty()) {
            throw new DadosInvalidosException("O ID do cliente não pode ser nulo ou vazio.");
        }

        try {
            return repositorioPagamento.listarPorCliente(clienteId);
        } catch (PagamentoRepositorioException e) {
            throw new PersistenciaException("Erro ao listar formas de pagamento por cliente.", e);
        }
    }
    // Método para verificar se o saldo do cartão de crédito é suficiente
    public boolean verificarSaldo(CartaoCredito cartaoCredito, double valor) throws DadosInvalidosException, PagamentoRecusadoException {
        if (cartaoCredito == null) { // Verifica se o cartão de crédito é nulo
            throw new DadosInvalidosException("Forma de pagamento inválida. O valor deve ser maior que zero.");
        }

        if (cartaoCredito.getGasto() > cartaoCredito.getLimite()) { // Verifica se o gasto é maior que o limite
            throw new PagamentoRecusadoException("Saldo insuficiente para realizar o pagamento.");
        }

        return true;
    }
    // Método para processar o pagamento
    public boolean  processarPagamento(CartaoCredito cartaoCredito, double valor) throws DadosInvalidosException, PagamentoRecusadoException {
        if(verificarSaldo(cartaoCredito, valor)){
            cartaoCredito.setGasto(cartaoCredito.getGasto() + valor); // Atualiza o gasto do cartão de crédito
            return true;
        } else {
            throw new PagamentoRecusadoException("Pagamento recusado. Saldo insuficiente.");
        }
    }   
}
