package ui;


import excecoes.DadosInvalidosException;
import excecoes.PagamentoRecusadoException;
import excecoes.VeiculoIncompativelException;
import java.util.Scanner;

public class ConsoleUI {

    public static void main(String[] args) throws PagamentoRecusadoException, VeiculoIncompativelException {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;
        
        MenuUsuarios menuUsuarios = new MenuUsuarios();
        MenuViagens menuViagens = new MenuViagens();

        do {
            try {
                System.out.println("\n=== Bem-Vindo ao DriveGO ===");
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Gerenciar Usuários");
                System.out.println("2. Gerenciar Viagens");
                System.out.println("3. Sair");
                System.out.print("Escolha uma opção: ");
                
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1 -> menuUsuarios.exibirMenu(scanner);
                    case 2 -> menuViagens.exibirMenu(scanner);
                    case 3 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número de 1 a 3.");
            } catch (DadosInvalidosException e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }

        } while (opcao != 3);

        scanner.close();
    }
}
