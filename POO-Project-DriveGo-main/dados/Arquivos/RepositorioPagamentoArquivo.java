package dados.Arquivos;

import dados.Interface.IRepositorioPagamento;
import excecoes.PagamentoRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.FormaDePagamento;

public class RepositorioPagamentoArquivo implements IRepositorioPagamento {

    private final String arquivo = "pagamentos.ser";
    private List<FormaDePagamento> pagamentos;

    public RepositorioPagamentoArquivo() throws PagamentoRepositorioException {
        this.pagamentos = carregarArquivo();
        atualizarIdCounter(pagamentos);
    }

    private void atualizarIdCounter(List<FormaDePagamento> pagamentos) {
        int maiorId = 0;
        for (FormaDePagamento pagamento : pagamentos) {
            int idAtual = Integer.parseInt(pagamento.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        FormaDePagamento.setIdCounter(maiorId + 1);
    }

    @Override
    public void salvarFormaDePagamento(FormaDePagamento formaDePagamento) throws PagamentoRepositorioException {
        pagamentos = listarFormasDePagamento();
        for (FormaDePagamento f : pagamentos) {
            if (f.getId().equals(formaDePagamento.getId())) {
                throw new PagamentoRepositorioException("Forma de pagamento já cadastrada com esse ID.");
            }
        }
        pagamentos.add(formaDePagamento);
        salvar(pagamentos);
        
    }

    @Override
    public FormaDePagamento buscarFormaDePagamento(String id) throws PagamentoRepositorioException {
        for (FormaDePagamento pagamento : listarFormasDePagamento()) {
            if (pagamento.getId().equals(id)) {
                return pagamento;
            }
        }
        return null;
    }

    @Override
    public void atualizarFormaDePagamento(FormaDePagamento formaDePagamento) throws PagamentoRepositorioException {
        pagamentos = listarFormasDePagamento();
        for (int i = 0; i < pagamentos.size(); i++) {
            if (pagamentos.get(i).getId().equals(formaDePagamento.getId())) {
                pagamentos.set(i, formaDePagamento);
                salvar(pagamentos);
                
                return;
            }
        }
        throw new PagamentoRepositorioException("Forma de pagamento não encontrada para atualização.");
    }

    @Override
    public void removerFormaDePagamento(FormaDePagamento formaDePagamento) throws PagamentoRepositorioException {
        pagamentos = listarFormasDePagamento();
        boolean removido = pagamentos.removeIf(p -> p.getId().equals(formaDePagamento.getId()));
        if (!removido) {
            throw new PagamentoRepositorioException("Forma de pagamento não encontrada para remoção.");
        }
        salvar(pagamentos);
        
    }

    @Override
    public List<FormaDePagamento> listarFormasDePagamento() throws PagamentoRepositorioException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<FormaDePagamento>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new PagamentoRepositorioException("Erro ao listar formas de pagamento.", e);
        }
    }

    @Override
    public List<FormaDePagamento> listarPorCliente(String clienteId) throws PagamentoRepositorioException {
        List<FormaDePagamento> pagamentosPorCliente = new ArrayList<>();
        for (FormaDePagamento pagamento : listarFormasDePagamento()) {
            if (pagamento.getClienteId() != null && pagamento.getClienteId().equals(clienteId)) {
                pagamentosPorCliente.add(pagamento);
            }
        }
        return pagamentosPorCliente;
    }

    private void salvar(List<FormaDePagamento> pagamentos) throws PagamentoRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(pagamentos);
        } catch (IOException e) {
            throw new PagamentoRepositorioException("Erro ao salvar formas de pagamento.", e);
        }
    }

    private List<FormaDePagamento> carregarArquivo() throws PagamentoRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<FormaDePagamento>) in.readObject();
        } catch (Exception e) {
            throw new PagamentoRepositorioException("Erro ao carregar o arquivo de formas de pagamento.", e);
        }
    }

   
}
