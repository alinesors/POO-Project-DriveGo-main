package ui;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.FormaDePagamentoNaoCadastradaException;
import excecoes.PagamentoRecusadoException;
import excecoes.PersistenciaException;
import excecoes.UsuarioRepositorioException;
import excecoes.VeiculoIncompativelException;
import excecoes.ViagemNaoAceitaException;
import excecoes.ViagemRepositorioException;
import java.util.List;
import java.util.Scanner;
import negocio.Fachada;
import negocio.basica.modelo.*;

public class MenuViagens { //Verificar a situação das cidades

    private static final Fachada fachada = Fachada.getInstancia();
    MenuCidades menuCidades = new MenuCidades();

    public void exibirMenu(Scanner scanner) throws PagamentoRecusadoException, VeiculoIncompativelException {
        int opcao = -1;
        do {
            try {
                System.out.println("\n=== Gerenciar Viagens ===");
                System.out.println("1. Cadastrar Viagem");
                System.out.println("2. Listar Viagens");
                System.out.println("3. Buscar Viagem por ID");
                System.out.println("4. Listar Viagens por Cliente");
                System.out.println("5. Listar Viagens por Motorista");
                System.out.println("6. Atualizar Viagem");
                System.out.println("7. Remover Viagem");
                System.out.println("8. Finalizar Viagem");
                System.out.println("9. Gerenciar cidades disponiveis");
                System.out.println("10. Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> cadastrarViagem(scanner);
                    case 2 -> listarViagens();
                    case 3 -> buscarViagemPorId(scanner);
                    case 4 -> listarViagensPorCliente(scanner);
                    case 5 -> listarViagensPorMotorista(scanner);
                    case 6 -> atualizarViagem(scanner);
                    case 7 -> removerViagem(scanner);
                    case 8 -> finalizarViagem(scanner);
                    case 9 -> menuCidades.exibirMenu(scanner);
                    case 10 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida!");
                }

            } catch (ViagemNaoAceitaException | UsuarioRepositorioException e) {
                System.out.println("Digite um número válido.");
            }
        } while (opcao != 10);
    }

    private void cadastrarViagem(Scanner scanner) throws ViagemNaoAceitaException, UsuarioRepositorioException, PagamentoRecusadoException, VeiculoIncompativelException { //verificar para entrega
        try {
            if(!fachada.haDisponibilidade()){
                System.out.println("Nenhum motorista disponível!");
                throw new ViagemNaoAceitaException("Nenhum motorista disponível! Viagem não pode ser aceita");
            }

            System.out.println("=== Cadastro de Viagem ===");

            System.out.print("ID do Cliente: ");
            String clienteId = scanner.nextLine();
            System.out.print("ID do Motorista: ");
            String motoristaId = scanner.nextLine();
            Motorista motorista = fachada.buscarMotoristaPorId(motoristaId);
            if(!motorista.isValidado()){
                System.out.println("Motorista invalidado! Não pode inciar uma corrida!");
                return;
            }
            if(!motorista.isDisponivel()){
                System.out.println("Motorista ocupado! Espere ele encerrar a corrida atual dele!");
                return;
            }

            System.out.println("\n=== Origem ===");
            Origem origem = coletarOrigem(scanner);

            System.out.println("\n=== Destino ===");
            Destino destino = coletarDestino(scanner);

            System.out.println("\n=== Valor da viagem ===");
            double valor = Integer.parseInt(scanner.nextLine());

            Cliente cliente = fachada.buscarClientePorId(clienteId);
            List<FormaDePagamento> formas = fachada.listarFormasDePagamentoPorCliente(clienteId);

            System.out.println("Forma de Pagamento:"); //Base no cliente
            for (FormaDePagamento f : formas) {
                System.out.println("ID: " + f.getId() + ", Tipo: " + f.getTipo());
            }
            System.out.print("Escolha o id: ");
            String escolha = scanner.nextLine();
            FormaDePagamento forma = fachada.buscarFormaDePagamentoPorId(escolha);

            if (forma == null) {
                throw new FormaDePagamentoNaoCadastradaException("Forma de pagamento inválida.");
            }

            if (forma instanceof CartaoCredito cartao) {
                if(!cartao.podePagar(valor)){
                    throw new PagamentoRecusadoException("Limite insuficiente para pagar.");
                }
                cartao.atualizarGasto(valor);
            }

            System.out.println("Tipo de Viagem:");
            System.out.println("1. Passageiro");
            System.out.println("2. Entrega");
            System.out.print("Escolha: ");
            int op = Integer.parseInt(scanner.nextLine());
            while (op != 1 && op != 2) {
                throw new DadosInvalidosException("Tipo de viagem inválido.");
            }

            Viagem viagem; 
            switch (op) {
                case 1 -> {
                    if(!destino.getCidade().isPermitirMoto() && motorista.getTipoVeiculo() instanceof Motocicleta) {
                        throw new VeiculoIncompativelException("Motocicleta não pode fazer corridas nessa cidade!");
                    }
                    viagem = new ViagemPassageiro(motorista, cliente, origem, destino, forma);
                }
                case 2 -> {
                    if(!destino.getCidade().isPermitirMoto() && motorista.getTipoVeiculo() instanceof Motocicleta) {
                        throw new VeiculoIncompativelException("Motocicleta não pode fazer entregas nessa cidade!");
                    }

                    System.out.println("Digite qual é o pacote:");
                    String pacote = scanner.nextLine();
                    viagem = new ViagemEntrega(motorista, cliente, origem, destino, forma, pacote);
                }
                default -> throw new AssertionError();
            }

            motorista.emCorrida();
            fachada.cadastrarViagem(viagem);
            System.out.println("Viagem cadastrada!");

        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException | ViagemRepositorioException | NumberFormatException | FormaDePagamentoNaoCadastradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private Origem coletarOrigem(Scanner scanner) throws DadosInvalidosException, PersistenciaException, EntidadeNaoEncontradaException {
        System.out.print("Cidade: ");
        List<Cidade> cidades = fachada.listarCidades();
        if (cidades.isEmpty()) {
            throw new DadosInvalidosException("Nenhuma cidade cadastrada. Cadastre uma cidade para utilizar o aplicativo");
        }
        for(Cidade cidade: cidades) {
            System.out.println(cidade.getId() + ". " + cidade.getNome());
        }
        System.out.print("Digite o id da cidade: ");
        String id = scanner.nextLine();
        Cidade cidade = fachada.buscarCidade(id);
        if (cidade == null) {
            throw new DadosInvalidosException("Cidade inválida.");
        }

        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();
        System.out.print("Ponto de Referência: ");
        String ponto = scanner.nextLine();

        return new Origem(rua, numero, bairro, cidade, estado, cep, complemento, ponto);
    }

    private Destino coletarDestino(Scanner scanner) throws DadosInvalidosException, PersistenciaException, EntidadeNaoEncontradaException {
        System.out.print("Cidade: ");
        List<Cidade> cidades = fachada.listarCidades();
        if (cidades.isEmpty()) {
            throw new DadosInvalidosException("Nenhuma cidade cadastrada. Cadastre uma cidade para utilizar o aplicativo");
        }
        for(Cidade cidade: cidades) {
            System.out.println(cidade.getId() + ". " + cidade.getNome());
        }
        System.out.print("Digite o id da cidade: ");
        String id = scanner.nextLine();
        Cidade cidade = fachada.buscarCidade(id);
        if (cidade == null) {
            throw new DadosInvalidosException("Cidade inválida.");
        }

        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();
        System.out.print("Ponto de Referência: ");
        String ponto = scanner.nextLine();

        return new Destino(rua, numero, bairro, cidade, estado, cep, complemento, ponto);
    }

    private void listarViagens() {
        try {
            List<Viagem> lista = fachada.listarViagens();
            System.out.println("\n=== Lista de Viagens ===");
            if (lista.isEmpty()) {
                System.out.println("Nenhuma viagem encontrada.");
            } else {
                for (Viagem v : lista) {
                    if(v.getCliente().getNome() == null || v.getMotorista().getNome() == null) {
                        System.out.println("ID: " + v.getId() + " | Cliente: Não encontrado | Motorista: Não encontrado" + " | Status: " + v.getStatus());
                    } else {
                        System.out.println("ID: " + v.getId() + " | Cliente: " + v.getCliente().getNome() + " | Motorista: " + v.getMotorista().getNome() + " | Status: " + v.getStatus());
                    }
                }
            }
        } catch (ViagemRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarViagemPorId(Scanner scanner) {
        try {
            System.out.print("ID da viagem: ");
            String id = scanner.nextLine();
            Viagem v = fachada.buscarViagemPorId(id);
            System.out.println("ID: " + v.getId() + " | Cliente: " + v.getCliente().getNome() + " | Motorista: " + v.getMotorista().getNome());
            System.out.println("status: " + v.getStatus());
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | ViagemRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarViagensPorCliente(Scanner scanner) {
        try {
            System.out.print("ID do Cliente: ");
            String id = scanner.nextLine();
            Cliente cliente = fachada.buscarClientePorId(id);
            List<Viagem> lista = fachada.listarViagensPorCliente(cliente);

            if (lista.isEmpty()) {
                System.out.println("Nenhuma viagem encontrada.");
            } else {
                for (Viagem v : lista) {
                    System.out.println("ID: " + v.getId() + " | Motorista: " + v.getMotorista().getNome());
                    System.out.println("status: " + v.getStatus());
                }
            }
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException | ViagemRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarViagensPorMotorista(Scanner scanner) {
        try {
            System.out.print("ID do Motorista: ");
            String id = scanner.nextLine();
            Motorista motorista = fachada.buscarMotoristaPorId(id);
            List<Viagem> lista = fachada.listarViagensPorMotorista(motorista);

            if (lista.isEmpty()) {
                System.out.println("Nenhuma viagem encontrada.");
            } else {
                for (Viagem v : lista) {
                    System.out.println("ID: " + v.getId() + " | Cliente: " + v.getCliente().getNome());
                    System.out.println("status: " + v.getStatus());
                }
            }
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | PersistenciaException | ViagemRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarViagem(Scanner scanner) {
        try {
            System.out.print("ID da viagem: ");
            String id = scanner.nextLine();
            Viagem v = fachada.buscarViagemPorId(id);

            if(v.getStatus().equals("Finalizada")) {
                System.out.println("Viagem já finalizada! Não pode ser atualizada.");
                return;
            }

            System.out.println("Digite o novo destino: ");
            Destino destino = coletarDestino(scanner);
            v.setDestino(destino);
            fachada.atualizarViagem(v);
            System.out.println("Viagem atualizada!");

        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | ViagemRepositorioException | NumberFormatException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerViagem(Scanner scanner) {
        try {
            System.out.print("ID da viagem: ");
            String id = scanner.nextLine();
            fachada.removerViagem(id);
            System.out.println("Viagem removida!");
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | ViagemRepositorioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void finalizarViagem(Scanner scanner) {
        try {
            System.out.print("ID da viagem: ");
            String id = scanner.nextLine();
            Viagem v = fachada.buscarViagemPorId(id);
            if(v.getStatus().equals("Finalizada")) {
                System.out.println("Viagem já finalizada! Não pode ser atualizada.");
                return;
            }
            v.setStatus("Finalizada");
            v.getMotorista().finalizarCorrida();

            System.out.println("Viagem finalizada!");
            fachada.atualizarViagem(v);

            System.out.println("Passageiro: Deseja avaliar a viagem? (S/N)");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")) {
                System.out.print("Digite a nota (1 a 5): ");
                int nota = Integer.parseInt(scanner.nextLine());
                while (nota < 1 || nota > 5) {
                    throw new DadosInvalidosException("Nota inválida. Deve ser entre 1 e 5.");
                }

                Avaliacao avaliacao = v.getMotorista().adicionarAvaliacao(nota);
                avaliacao.setViagemId(v.getId());
                avaliacao.setPessoaId(v.getMotorista().getId());
                fachada.cadastrarAvaliacao(avaliacao);
                System.out.println("Avaliação registrada!");
            } else {
                System.out.println("Avaliação não registrada.");
            }

            System.out.println("Motorista: Deseja avaliar o passageiro? (S/N)");
            resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")) {
                System.out.print("Digite a nota (1 a 5): ");
                int nota = Integer.parseInt(scanner.nextLine());
                while (nota < 1 || nota > 5) {
                    throw new DadosInvalidosException("Nota inválida. Deve ser entre 1 e 5.");
                }

                Avaliacao avaliacao = v.getMotorista().adicionarAvaliacao(nota);
                avaliacao.setViagemId(v.getId());
                avaliacao.setPessoaId(v.getCliente().getId());
                fachada.cadastrarAvaliacao(avaliacao);
                System.out.println("Avaliação registrada!");
            } else {
                System.out.println("Avaliação não registrada.");
            }
        } catch (DadosInvalidosException | EntidadeNaoEncontradaException | ViagemRepositorioException | PersistenciaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
