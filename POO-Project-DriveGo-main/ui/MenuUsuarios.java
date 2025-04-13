package ui;

import excecoes.AvaliacaoRepositorioException;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import excecoes.VeiculoRepositorioException;
import java.util.Scanner;
import negocio.Fachada;
import negocio.basica.modelo.*;

public class MenuUsuarios {

    private static final Fachada fachada = Fachada.getInstancia();
    MenuPagamentos menuPagamentos = new MenuPagamentos();

    public void exibirMenu(Scanner scanner) throws DadosInvalidosException {
        int opcao = -1;
        do {
            try {
                System.out.println("\n=== Gerenciar Usuários ===");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Cadastrar Motorista");
                System.out.println("3. Listar Usuários");
                System.out.println("4. Buscar Usuário por ID");
                System.out.println("5. Atualizar Usuário");
                System.out.println("6. Remover Usuário");
                System.out.println("7. Listar carros cadastrados.");
                System.out.println("8. Gerenciar forma de pagamento de um cliente.");
                System.out.println("9. Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); 
                switch (opcao) {
                    case 1 -> cadastrarCliente(scanner);
                    case 2 -> cadastrarMotorista(scanner);
                    case 3 -> listarUsuarios();
                    case 4 -> buscarUsuarioPorId(scanner);
                    case 5 -> atualizarUsuario(scanner);
                    case 6 -> removerUsuario(scanner);
                    case 7 -> listarCarros();
                    case 8 -> {
                        System.out.println("Digite o ID do cliente: ");
                        String id = scanner.nextLine();
                        Cliente cliente = fachada.buscarClientePorId(id);
                        menuPagamentos.exibirMenu(scanner, cliente);
                    }
                    case 9 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (EntidadeNaoEncontradaException | PersistenciaException | VeiculoRepositorioException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 9);
    }

    private void cadastrarCliente(Scanner scanner) {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Cliente cliente = fachada.cadastrarCliente(nome, cpf, telefone, endereco, email);
            Dinheiro dinheiro = new Dinheiro();
            dinheiro.setClienteId(cliente.getId());
            cliente.adicionarFormaDePagamento(dinheiro);
            fachada.cadastrarFormaDePagamento(dinheiro);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (DadosInvalidosException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarMotorista(Scanner scanner) {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("CNH: ");
            String cnh = scanner.nextLine();

            System.out.println("\n=== Tipo de Veículo ===");
            System.out.println("1. SUV");
            System.out.println("2. Motocicleta");
            System.out.println("3. Luxo");
            System.out.println("4. Econômico");
            System.out.print("Escolha uma opção: ");
            Veiculo veiculo = criarVeiculo(scanner);
            if (veiculo == null) {
                System.out.println("Veículo inválido!");
                return;
            }

            Motorista motorista = fachada.cadastrarMotorista(nome, cpf, telefone, endereco, email, cnh, veiculo);

            veiculo.setMotorista(motorista);
            fachada.cadastrarVeiculo(veiculo);

            System.out.println("Motorista cadastrado com sucesso!");
            System.out.println("Tentar validar o motorista? (1 - Sim, 2 - Não): ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 -> {
                    motorista.validarMotorista(motorista.getCnh(), motorista.getCpf(), motorista.getTipoVeiculo());
                    if(motorista.isValidado()) {
                        motorista.setValidado(true);
                        fachada.atualizarUsuario(motorista);
                        System.out.println("Motorista validado com sucesso!");
                    } else {
                        System.out.println("Motorista com dados invalidos, atualize imediatamente!");
                    }
                }
                case 2 -> System.out.println("Motorista não validado.");
                default -> System.out.println("Opção inválida!");
            }


        }catch (DadosInvalidosException | PersistenciaException | VeiculoRepositorioException | EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        try {
            System.out.println("\nClientes:");
            for (Cliente c : fachada.listarClientes()) {
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
            }

            System.out.println("\nMotoristas:");
            for (Motorista m : fachada.listarMotoristas()) {
                System.out.println("ID: " + m.getId() + " | Nome: " + m.getNome());
            }
        } catch (PersistenciaException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorId(Scanner scanner) {
        try {
            System.out.print("ID do usuário: ");
            String id = scanner.nextLine();
            Pessoa p = fachada.buscarUsuarioPorId(id);
            System.out.println("Usuário encontrado: " + p.getNome());
            switch (p) {
                case Motorista motorista -> {
                    System.out.println("Tipo: Motorista");
                    System.out.println("CPF: " + motorista.getCpf());
                    System.out.println("CNH: " + motorista.getCnh());
                    System.out.println("Tipo de Veículo: " + motorista.getTipoVeiculo().getModelo());
                    System.out.println("Disponibilidade: " + motorista.isDisponivel());
                    System.out.println("Validado: " + motorista.isValidado());
                    System.out.println("Nota: " + fachada.notaMediaAvaliacao(motorista.getId()));
                }
                case Cliente cliente -> {
                    System.out.println("Tipo: Cliente");
                    System.out.println("CPF: " + cliente.getCpf());
                    System.out.println("Nota: " + fachada.notaMediaAvaliacao(cliente.getId()));
                    System.out.println("Formas de pagamento: ");
                    for (FormaDePagamento c : fachada.listarFormasDePagamentoPorCliente(id)) {
                        System.out.println(c.getTipo());
                    }
                }
                default -> {
                }
            }
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException | AvaliacaoRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarUsuario(Scanner scanner) throws VeiculoRepositorioException {
        try {
            System.out.print("ID do usuário: ");
            String id = scanner.nextLine();
            Pessoa p = fachada.buscarUsuarioPorId(id);

            System.out.print("Novo nome (atual: " + p.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) p.setNome(nome);

            System.out.print("Novo telefone (atual: " + p.getTelefone() + "): ");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) p.setTelefone(telefone);

            System.out.print("Novo CPF (atual: " + p.getCpf() + "): ");
            String cpf = scanner.nextLine();
            if (!cpf.isEmpty()) p.setCpf(cpf);

            if(p instanceof Motorista motorista) {
                System.out.print("Novo CNH (atual: " + motorista.getCnh() + "): ");
                String cnh = scanner.nextLine();
                if (!cnh.isEmpty()) motorista.setCnh(cnh);

                System.out.print("Tipo veiculo (atual: " + motorista.getTipoVeiculo().getModelo() + "): ");
                System.out.println("\n=== Tipo de Veículo ===");
                System.out.println("1. SUV");
                System.out.println("2. Motocicleta");
                System.out.println("3. Luxo");
                System.out.println("4. Econômico");
                System.out.print("Escolha uma opção: ");
                Veiculo veiculo = atualizarVeiculo(scanner, motorista.getTipoVeiculo());
                if (veiculo == null) {
                    System.out.println("Veículo inválido!");
                    return;
                }
                fachada.atualizarVeiculo(veiculo);

                System.out.println("Tentar validar o motorista? (1 - Sim, 2 - Não): ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        motorista.validarMotorista(motorista.getCnh(), motorista.getCpf(), motorista.getTipoVeiculo());
                        if(motorista.isValidado()) {
                            System.out.println("Motorista validado com sucesso!");
                        } else {
                            System.out.println("Motorista com dados invalidos, atualize imediatamente!");
                        }
                    }
                    case 2 -> System.out.println("Motorista não validado.");
                    default -> System.out.println("Opção inválida!");
                }
            }

            fachada.atualizarUsuario(p);
            System.out.println("Usuário atualizado!");
            

        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerUsuario(Scanner scanner) {
        try {
            System.out.print("ID do usuário a remover: ");
            String id = scanner.nextLine();
            Pessoa p = fachada.buscarUsuarioPorId(id);
            if (p instanceof Motorista motorista) {
                fachada.removerVeiculo(motorista.getTipoVeiculo().getId());
            }
            fachada.removerUsuario(id);
            System.out.println("Removido com sucesso!");
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException | VeiculoRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarCarros() {
        try {
            System.out.println("Carros:");
            for (Veiculo v : fachada.listarVeiculos()) {
                if(v.getMotorista() == null) {
                    continue;
                }
                System.out.println("ID: " + v.getId() + "| Nome motorista: " + v.getMotorista().getNome() +"| Tipo: " + v.getModelo());
                System.out.println(" Placa: " + v.getPlaca() + " | Ano: " + v.getAnoFabricacao() + " | Cor: " + v.getCor() + " | Marca: " + v.getMarca());
            }
        } catch (VeiculoRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private Veiculo criarVeiculo(Scanner scanner) {
        try {
            int tipo = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner
            while(tipo < 1 || tipo > 4) {
                System.out.println("Tipo inválido! Tente novamente: ");
                tipo = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner
            }
            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            System.out.print("Ano de fabricação: ");
            int ano = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            return switch (tipo) {
                case 1 -> new SUV("SUV", marca, cor, ano, placa);
                case 2 -> new Motocicleta("Motocicleta", marca, cor, ano, placa);
                case 3 -> new Luxo("Luxo", marca, cor, ano, placa);
                case 4 -> new Economico("Econômico", marca, cor, ano, placa);
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Numero não inserido corretamente!");
            return null;
        } 
    }

    @SuppressWarnings("UseSpecificCatch")
    private Veiculo atualizarVeiculo(Scanner scanner, Veiculo veiculo) {
        try {
            int tipo = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner
            while(tipo < 1 || tipo > 4) {
                System.out.println("Tipo inválido! Tente novamente: ");
                tipo = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner
            }
            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            System.out.print("Ano de fabricação: ");
            int ano = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            return switch (tipo) {
                case 1 -> {
                    veiculo.setModelo("SUV");
                    veiculo.setMarca(marca);
                    veiculo.setCor(cor);
                    veiculo.setAnoFabricacao(ano);
                    veiculo.setPlaca(placa);
                    yield veiculo;
                }
                case 2 -> {
                    veiculo.setModelo("Motocicleta");
                    veiculo.setMarca(marca);
                    veiculo.setCor(cor);
                    veiculo.setAnoFabricacao(ano);
                    veiculo.setPlaca(placa);
                    yield veiculo;
                }
                case 3 -> {
                    veiculo.setModelo("Luxo");
                    veiculo.setMarca(marca);
                    veiculo.setCor(cor);
                    veiculo.setAnoFabricacao(ano);
                    veiculo.setPlaca(placa);
                    yield veiculo;
                }
                case 4 -> {
                    veiculo.setModelo("Econômico");
                    veiculo.setMarca(marca);
                    veiculo.setCor(cor);
                    veiculo.setAnoFabricacao(ano);
                    veiculo.setPlaca(placa);
                    yield veiculo;
                }
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Numero não inserido corretamente!");
            return null;
        } 
    }
}
