package ui;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import java.util.Scanner;
import negocio.Fachada;
import negocio.basica.modelo.CartaoCredito;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Dinheiro;
import negocio.basica.modelo.FormaDePagamento;
import negocio.basica.modelo.Pix;

public class MenuPagamentos {

    private static final Fachada fachada = Fachada.getInstancia();

    public void exibirMenu(Scanner scanner, Cliente cliente) throws DadosInvalidosException {
        int opcao = -1;
        do {
            try {
                System.out.println("\n=== Gerenciar Pagamentos ===");
                System.out.println("1. Cadastrar Forma de Pagamento");
                System.out.println("2. Listar Formas de Pagamento");
                System.out.println("3. Atualizar Forma de Pagamento");
                System.out.println("4. Remover Forma de Pagamento");
                System.out.println("5. Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> cadastrarFormaDePagamento(scanner, cliente);
                    case 2 -> listarFormasDePagamento(cliente);
                    case 3 -> atualizarFormaDePagamento(scanner);
                    case 4 -> removerFormaDePagamento(scanner);
                    case 5 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 5);
    }

    private void cadastrarFormaDePagamento(Scanner scanner, Cliente cliente) {
        try {
            System.out.println("Tipo:");
            System.out.println("1. Cartão de Crédito");
            System.out.println("2. Pix");
            System.out.print("Escolha uma opção: ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            FormaDePagamento forma;
            switch (tipo) {
                case 1 -> {
                    System.out.print("Limite do cartão: ");
                    double limite = Double.parseDouble(scanner.nextLine());
                    forma = new CartaoCredito(limite);
                    forma.setClienteId(cliente.getId());
                }
                case 2 -> {
                    System.out.print("Escreva a chave pix: ");
                    String chavePix = scanner.nextLine();
                    forma = new Pix(chavePix);
                    forma.setClienteId(cliente.getId());
                }
                default -> {
                    System.out.println("Tipo inválido.");
                    forma = null;
                }
            }

            if (forma != null) {
                cliente.adicionarFormaDePagamento(forma);
                fachada.cadastrarFormaDePagamento(forma);
                System.out.println("Pagamento cadastrado com sucesso!");
            }

        } catch (DadosInvalidosException | PersistenciaException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void listarFormasDePagamento(Cliente cliente) throws DadosInvalidosException {
        try {
            List<FormaDePagamento> lista = fachada.listarFormasDePagamentoPorCliente(cliente.getId());
            System.out.println("\n=== Lista de Pagamentos ===");
            if (lista.isEmpty()) {
                System.out.println("Nenhum pagamento cadastrado.");
            } else {
                for (FormaDePagamento f : lista) {
                    System.out.println("ID: " + f.getId() + ", Tipo: " + f.getTipo());
                }
            }
        } catch (PersistenciaException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }

    private void atualizarFormaDePagamento(Scanner scanner) {
        try {
            System.out.print("ID do pagamento: ");
            String id = scanner.nextLine();
            FormaDePagamento f = fachada.buscarFormaDePagamentoPorId(id);

            if (f instanceof CartaoCredito cartaoCredito) {
                System.out.print("Novo limite: ");
                double novoLimite = Double.parseDouble(scanner.nextLine());
                cartaoCredito.setLimite(novoLimite);
            }

            if (f instanceof Pix pix) {
                System.out.print("Nova chave pix: ");
                String novaChavePix = scanner.nextLine();
                pix.setChavePix(novaChavePix);
            }

            if (f instanceof Dinheiro) {
                System.out.print("Não há como atualizar o dinheiro! ");
                return;
            }

            fachada.atualizarFormaDePagamento(f);
            System.out.println("Pagamento atualizado!");

        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void removerFormaDePagamento(Scanner scanner) {
        try {
            System.out.print("ID do pagamento a remover: ");
            String id = scanner.nextLine();
            FormaDePagamento f = fachada.buscarFormaDePagamentoPorId(id);
            if (f instanceof Dinheiro) {
                System.out.print("Não há como remover a opção dinheiro! ");
                return;
            }
            fachada.removerFormaDePagamento(id);
            System.out.println("Removido com sucesso!");
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

}
