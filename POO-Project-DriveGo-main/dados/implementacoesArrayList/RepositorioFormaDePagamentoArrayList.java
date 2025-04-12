package dados.implementacoesArrayList;

import dados.Interface.IRepositorioPagamento;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.FormaDePagamento;

public class RepositorioFormaDePagamentoArrayList implements IRepositorioPagamento {

    private final List<FormaDePagamento> formaDePagamentos;

    public RepositorioFormaDePagamentoArrayList() {
        this.formaDePagamentos = new ArrayList<>();
    }

    public RepositorioFormaDePagamentoArrayList(List<FormaDePagamento> formaDePagamentos) {
        this.formaDePagamentos = formaDePagamentos;
    }

    @Override // Método para buscar uma forma de pagamento pelo ID
    public FormaDePagamento buscarFormaDePagamento(String id) {
        for (FormaDePagamento pagamento : formaDePagamentos) {
            if (String.valueOf(pagamento.getId()).equals(id)) {
                return pagamento;
            }
        }
        return null;
    }

    @Override // Método para buscar uma forma de pagamento pelo ID do cliente
    public void atualizarFormaDePagamento(FormaDePagamento formaDePagamento) {
        for (int i = 0; i < formaDePagamentos.size(); i++) {
            if (formaDePagamentos.get(i).getId().equals(formaDePagamento.getId())) {
                formaDePagamentos.set(i, formaDePagamento);
                return;
            }
        }
    }

    @Override // Método para remover uma forma de pagamento
    public void removerFormaDePagamento(FormaDePagamento formaDePagamento) {
        formaDePagamentos.remove(formaDePagamento);
    }

    @Override // Método para salvar uma nova forma de pagamento
    public void salvarFormaDePagamento(FormaDePagamento entidade) {
        formaDePagamentos.add(entidade);
    }

    @Override // Método para listar todas as formas de pagamento
    public List<FormaDePagamento> listarFormasDePagamento() {
        return new ArrayList<>(formaDePagamentos);
    }

    @Override // Método para listar formas de pagamento por cliente
    public List<FormaDePagamento> listarPorCliente(String clienteId) {
        List<FormaDePagamento> pagamentosPorCliente = new ArrayList<>();
        for (FormaDePagamento pagamento : formaDePagamentos) {
            if (pagamento.getClienteId() != null && pagamento.getClienteId().equals(clienteId)) { // Verifica se o clienteId não é nulo e se corresponde ao ID do cliente
                pagamentosPorCliente.add(pagamento);
            }
        }
        return pagamentosPorCliente;
    }
}