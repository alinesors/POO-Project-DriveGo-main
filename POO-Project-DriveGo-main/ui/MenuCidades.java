package ui;


import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import java.util.List;
import java.util.Scanner;
import negocio.Fachada;
import negocio.basica.modelo.*;

public class MenuCidades {

    private static final Fachada fachada = Fachada.getInstancia();

    public void exibirMenu(Scanner scanner) {
        int opcao = -1;
        do {
            try {
                System.out.println("\n=== Gerenciar Cidades ===");
                System.out.println("1. Cadastrar Cidade");
                System.out.println("2. Listar Cidades");
                System.out.println("3. Atualizar Cidade");
                System.out.println("4. Remover Cidade");
                System.out.println("5. Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> cadastrarCidade(scanner);
                    case 2 -> listarCidades();
                    case 3 -> atualizarCidade(scanner);
                    case 4 -> removerCidade(scanner);
                    case 5 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Digite um número válido.");
            }
        } while (opcao != 5);
    }

    private void cadastrarCidade(Scanner scanner) {
        try {
            System.out.println("Digite o nome da cidade:");
            String nome = scanner.nextLine();
            System.out.println("Permitir entrega e viagem por moto? (S/N):");
            String permitirMotoStr = scanner.nextLine();
            Cidade cidade;
            switch (permitirMotoStr.toUpperCase()) {
                case "S" -> {
                    cidade = new Cidade(nome, true);
                    fachada.cadastrarCidade(cidade);
                }
                case "N" -> {
                    cidade = new Cidade(nome, false);
                    fachada.cadastrarCidade(cidade);
                }
                default -> {
                    System.out.println("Opção inválida! Tente novamente.");
                    return;
                }
            }


            System.out.println("Cidade cadastrado!");

        } catch (DadosInvalidosException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarCidades() {
        try {
            List<Cidade> lista = fachada.listarCidades();
            System.out.println("\n=== Lista de Cidades ===");
            if (lista.isEmpty()) {
                System.out.println("Nenhuma cidade cadastrada.");
            } else {
                for (Cidade c : lista) {
                    System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Permite moto: " + c.isPermitirMoto());
                }
            }
        } catch (PersistenciaException e) {
            System.out.println("Erro ao listar Cidades: " + e.getMessage());
        }
    }


    private void atualizarCidade(Scanner scanner) {
        try {
            System.out.print("ID: ");
            String id = scanner.nextLine();
            Cidade cidade = fachada.buscarCidade(id);
            System.out.println("Digite o novo nome da cidade:");
            String nome = scanner.nextLine();
            System.out.println("Permitir entrega e viagem por moto? (S/N):");
            String permitirMotoStr = scanner.nextLine();
            cidade.setNome(nome);
            switch (permitirMotoStr.toUpperCase()) {
                case "S" -> {
                    cidade.setPermitirMoto(true);
                    fachada.atualizarCidade(cidade);
                }
                case "N" -> {
                    cidade.setPermitirMoto(false);
                    fachada.atualizarCidade(cidade);
                }
                default -> {
                    System.out.println("Opção inválida! Tente novamente.");
                    return;
                }
            }

            
            System.out.println("Cidade cadastrado!");

        } catch (DadosInvalidosException  | PersistenciaException | EntidadeNaoEncontradaException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void removerCidade(Scanner scanner) {
        try {
            System.out.print("ID: ");
            String id = scanner.nextLine();
            Cidade cidade = fachada.buscarCidade(id);
            fachada.removerCidade(cidade.getId());
            System.out.println("Removido com sucesso!");
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
