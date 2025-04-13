package negocio;

import excecoes.*;
import java.util.List;
import negocio.basica.modelo.*;

//essa classe é a fachada do sistema, ela é responsável por fornecer uma interface única para o sistema
public class Fachada {

    private static Fachada instancia;

    private final GerenciadorCliente gerenciadorCliente;
    private final GerenciadorMotorista gerenciadorMotorista;
    private final GerenciadorVeiculos gerenciadorVeiculos;
    private final GerenciadorViagens gerenciadorViagens;
    private final GerenciadorPagamento gerenciadorPagamento;
    private final GerenciadorAvaliacao gerenciadorAvaliacao;
    private final GerenciadorCidade gerenciadorCidade;


    private Fachada() throws Exception {
        this.gerenciadorCliente = new GerenciadorCliente(new servico.UsuarioServico());
        this.gerenciadorMotorista = new GerenciadorMotorista(new servico.UsuarioServico());
        this.gerenciadorVeiculos = new GerenciadorVeiculos(new servico.VeiculoServico());
        this.gerenciadorViagens = new GerenciadorViagens(new servico.ViagemServico());
        this.gerenciadorPagamento = new GerenciadorPagamento(new servico.PagamentoServico());
        this.gerenciadorAvaliacao = new GerenciadorAvaliacao(new servico.AvaliacaoServico());
        this.gerenciadorCidade = new GerenciadorCidade(new servico.CidadeServico());
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            try {
                instancia = new Fachada();
            } catch (Exception e) {
                System.err.println("Erro ao instanciar a fachada: " + e.getMessage());
                throw new RuntimeException("Falha crítica ao iniciar o sistema.");
            }
        }
        return instancia;
    }

    public Cliente cadastrarCliente(String nome, String cpf, String telefone, String endereco, String email) throws DadosInvalidosException, PersistenciaException {
        return gerenciadorCliente.cadastrarCliente(nome, cpf, telefone, endereco, email);
    }

    public List<Cliente> listarClientes() throws PersistenciaException {
        return gerenciadorCliente.listarClientes();
    }

    public Cliente buscarClientePorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return gerenciadorCliente.buscarClientePorId(id);
    }

    public void atualizarCliente(Cliente cliente) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorCliente.atualizarCliente(cliente);
    }

    public void removerCliente(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorCliente.removerCliente(id);
    }

    public Motorista cadastrarMotorista(String nome, String cpf, String telefone, String endereco, String email, String cnh, Veiculo veiculo) throws DadosInvalidosException, PersistenciaException {
        return gerenciadorMotorista.cadastrarMotorista(nome, cpf, telefone, endereco, email, cnh, veiculo);
    }

    public List<Motorista> listarMotoristas() throws PersistenciaException {
        return gerenciadorMotorista.listarMotoristas();
    }

    public Motorista buscarMotoristaPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return gerenciadorMotorista.buscarMotoristaPorId(id);
    }

    public void atualizarMotorista(Motorista motorista) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorMotorista.atualizarMotorista(motorista);
    }

    public void removerMotorista(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorMotorista.removerMotorista(id);
    }

    public Pessoa buscarUsuarioPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        Pessoa usuario = gerenciadorCliente.buscarClientePorId(id);
        if (usuario == null) {
            usuario = gerenciadorMotorista.buscarMotoristaPorId(id);
        }
        return usuario;
    }

    public void atualizarUsuario(Pessoa usuario) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        switch (usuario) {
            case Cliente cliente -> gerenciadorCliente.atualizarCliente(cliente);
            case Motorista motorista -> gerenciadorMotorista.atualizarMotorista(motorista);
            default -> throw new IllegalArgumentException("Tipo de usuário desconhecido.");
        }
    }

    public void removerUsuario(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        Pessoa usuario = buscarUsuarioPorId(id);
        if (usuario instanceof Cliente) {
            gerenciadorCliente.removerCliente(id);
        } else if (usuario instanceof Motorista) {
            gerenciadorMotorista.removerMotorista(id);
        } else {
            throw new IllegalArgumentException("Usuário não é nem cliente nem motorista.");
        }
    }

    public void cadastrarVeiculo(Veiculo veiculo) throws DadosInvalidosException, VeiculoRepositorioException {
        gerenciadorVeiculos.cadastrarVeiculo(veiculo);
    }

    public List<Veiculo> listarVeiculos() throws VeiculoRepositorioException {
        return gerenciadorVeiculos.listarVeiculos();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) throws DadosInvalidosException, VeiculoRepositorioException, EntidadeNaoEncontradaException {
        return gerenciadorVeiculos.buscarVeiculoPorPlaca(placa);
    }

    public void atualizarVeiculo(Veiculo veiculo) throws DadosInvalidosException, VeiculoRepositorioException, EntidadeNaoEncontradaException {
        gerenciadorVeiculos.atualizarVeiculo(veiculo);
    }

    public void removerVeiculo(String id) throws DadosInvalidosException, VeiculoRepositorioException, EntidadeNaoEncontradaException {
        gerenciadorVeiculos.removerVeiculo(id);
    }

    public List<Veiculo> listarVeiculosPorTipo(String tipo) throws DadosInvalidosException, VeiculoRepositorioException {
        return gerenciadorVeiculos.listarVeiculosPorTipo(tipo);
    }

    public void cadastrarViagem(Viagem viagem) throws DadosInvalidosException, ViagemRepositorioException {
        gerenciadorViagens.cadastrarViagem(viagem);
    }

    public List<Viagem> listarViagens() throws ViagemRepositorioException {
        return gerenciadorViagens.listarViagens();
    }

    public Viagem buscarViagemPorId(String id) throws DadosInvalidosException, ViagemRepositorioException, EntidadeNaoEncontradaException {
        return gerenciadorViagens.buscarViagemPorId(id);
    }

    public void atualizarViagem(Viagem viagem) throws DadosInvalidosException, ViagemRepositorioException, EntidadeNaoEncontradaException {
        gerenciadorViagens.atualizarViagem(viagem);
    }

    public void removerViagem(String id) throws DadosInvalidosException, ViagemRepositorioException, EntidadeNaoEncontradaException {
        gerenciadorViagens.removerViagem(id);
    }

    public List<Viagem> listarViagensPorCliente(Cliente cliente) throws DadosInvalidosException, ViagemRepositorioException {
        return gerenciadorViagens.listarViagensPorCliente(cliente);
    }

    public List<Viagem> listarViagensPorMotorista(Motorista motorista) throws DadosInvalidosException, ViagemRepositorioException {
        return gerenciadorViagens.listarViagensPorMotorista(motorista);
    }

    public void registrarAvaliacao(String viagemId, double notaCliente, String comentarioCliente, double notaMotorista, String comentarioMotorista) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException, ViagemRepositorioException {
        gerenciadorViagens.registrarAvaliacao(viagemId, notaCliente, comentarioCliente, notaMotorista, comentarioMotorista);
    }

    public void cadastrarFormaDePagamento(FormaDePagamento formaDePagamento) throws DadosInvalidosException, PersistenciaException {
        gerenciadorPagamento.cadastrarFormaDePagamento(formaDePagamento);
    }

    public List<FormaDePagamento> listarFormasDePagamento() throws PersistenciaException {
        return gerenciadorPagamento.listarFormasDePagamento();
    }

    public FormaDePagamento buscarFormaDePagamentoPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return gerenciadorPagamento.buscarFormaDePagamentoPorId(id);
    }

    public void atualizarFormaDePagamento(FormaDePagamento formaDePagamento) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorPagamento.atualizarFormaDePagamento(formaDePagamento);
    }

    public void removerFormaDePagamento(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorPagamento.removerFormaDePagamento(id);
    }

    public List<FormaDePagamento> listarFormasDePagamentoPorCliente(String clienteId) throws DadosInvalidosException, PersistenciaException {
        return gerenciadorPagamento.listarFormasDePagamentoPorCliente(clienteId);
    }

    public void cadastrarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, PersistenciaException {
        gerenciadorAvaliacao.cadastrarAvaliacao(avaliacao);
    }

    public Avaliacao buscarAvaliacaoPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return gerenciadorAvaliacao.buscarAvaliacao(id);
    }

    public void atualizarAvaliacao(Avaliacao avaliacao) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorAvaliacao.atualizarAvaliacao(avaliacao);
    }

    public void removerAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorAvaliacao.removerAvaliacao(id);
    }

    public List<Avaliacao> listarAvaliacoes() throws PersistenciaException {
        return gerenciadorAvaliacao.listarAvaliacoes();
    }

    public boolean processarPagamento(CartaoCredito cartaoCredito, double saldo) throws DadosInvalidosException, PersistenciaException, PagamentoRecusadoException {
        return gerenciadorPagamento.processarPagamento(cartaoCredito, saldo);
    }

    public boolean haDisponibilidade() throws ViagemRepositorioException, DadosInvalidosException, ViagemNaoAceitaException, UsuarioRepositorioException {
        return gerenciadorViagens.haDisponibilidade();
    }

    /**
     * Método para cadastrar uma cidade
     * @param cidade A cidade a ser cadastrada
     * @throws DadosInvalidosException Se os dados da cidade forem inválidos
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void cadastrarCidade(Cidade cidade) throws DadosInvalidosException, PersistenciaException {
        gerenciadorCidade.cadastrarCidade(cidade);
    }

    /**
     * Método para listar todas as cidades
     * @return Lista de todas as cidades cadastradas
     * @throws PersistenciaException Se ocorrer um erro na recuperação dos dados
     */
    public List<Cidade> listarCidades() throws PersistenciaException {
        return gerenciadorCidade.listarCidades();
    }

    /**
     * Método para buscar uma cidade pelo nome
     * @param nome O nome da cidade a ser buscada
     * @return A cidade encontrada
     * @throws DadosInvalidosException Se o nome for nulo ou vazio
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na recuperação dos dados
     */
    public Cidade buscarCidade(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        return gerenciadorCidade.buscarCidade(id);
    }

    /**
     * Método para atualizar uma cidade
     * @param cidade A cidade com os dados atualizados
     * @throws DadosInvalidosException Se os dados da cidade forem inválidos
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void atualizarCidade(Cidade cidade) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorCidade.atualizarCidade(cidade);
    }

    /**
     * Método para remover uma cidade
     * @param nome O nome da cidade a ser removida
     * @throws DadosInvalidosException Se o nome for nulo ou vazio
     * @throws EntidadeNaoEncontradaException Se a cidade não for encontrada
     * @throws PersistenciaException Se ocorrer um erro na persistência dos dados
     */
    public void removerCidade(String nome) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        gerenciadorCidade.removerCidade(nome);
    }

    public double notaMediaAvaliacao(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException, AvaliacaoRepositorioException {
        return gerenciadorAvaliacao.atualizarNotaMedia(id);
    }
}