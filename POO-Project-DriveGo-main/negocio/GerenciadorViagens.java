package negocio;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.UsuarioRepositorioException;
import excecoes.ViagemNaoAceitaException;
import excecoes.ViagemRepositorioException;
import java.util.List;
import negocio.basica.modelo.Avaliacao;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Viagem;
import servico.ViagemServico;

/**
 * Classe responsável por gerenciar as operações relacionadas às viagens.
 * Esta classe atua como uma camada intermediária entre a interface de usuário e o serviço de viagem.
 */
public class GerenciadorViagens {

    private final ViagemServico viagemServico;

    public GerenciadorViagens(ViagemServico viagemServico) {
        this.viagemServico = viagemServico;
    }

    public GerenciadorViagens() throws ViagemRepositorioException, UsuarioRepositorioException {
        this.viagemServico = new ViagemServico();
    }

    // Método para cadastrar uma nova viagem
    public void cadastrarViagem(Viagem viagem) throws DadosInvalidosException, ViagemRepositorioException {
        viagemServico.cadastrarViagem(viagem); // Chama o método do serviço de viagem para cadastrar a viagem
    }

    // Método para listar todas as viagens
    public List<Viagem> listarViagens() throws ViagemRepositorioException {
        return viagemServico.listarViagens(); // Chama o método do serviço de viagem para listar todas as viagens
    }
    // Método para buscar uma viagem por ID
    public Viagem buscarViagemPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, ViagemRepositorioException {
        return viagemServico.buscarViagem(id); // Chama o método do serviço de viagem para buscar uma viagem pelo ID
    }   
    // Método para atualizar uma viagem
    public void atualizarViagem(Viagem viagem) throws DadosInvalidosException, EntidadeNaoEncontradaException, ViagemRepositorioException {
        viagemServico.atualizarViagem(viagem); // Chama o método do serviço de viagem para atualizar uma viagem
    }
    // Método para remover uma viagem
    public void removerViagem(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, ViagemRepositorioException {
        viagemServico.removerViagem(id);
    }
    // Método para listar viagens por cliente
    public List<Viagem> listarViagensPorCliente(Cliente cliente) throws DadosInvalidosException, ViagemRepositorioException {
        return viagemServico.listarViagensPorCliente(cliente);
    }
    // Método para listar viagens por motorista
    public List<Viagem> listarViagensPorMotorista(Motorista motorista) throws DadosInvalidosException, ViagemRepositorioException {
        return viagemServico.listarViagensPorMotorista(motorista);
    }
    // Método para registrar uma avaliação de viagem
    public void registrarAvaliacao(String viagemId, double notaCliente, String comentarioCliente, double notaMotorista, String comentarioMotorista) throws 
        DadosInvalidosException, EntidadeNaoEncontradaException, ViagemRepositorioException {

        Viagem viagem = viagemServico.buscarViagem(viagemId);

        Avaliacao avaliacaoCliente = new Avaliacao(notaCliente); // Cria uma nova avaliação para o cliente
        // Cria uma nova avaliação para o cliente
        viagem.setAvaliacaoCliente(avaliacaoCliente);

        Avaliacao avaliacaoMotorista = new Avaliacao(notaMotorista); // Cria uma nova avaliação para o motorista
        viagem.setAvaliacaoMotorista(avaliacaoMotorista);

        viagemServico.atualizarViagem(viagem);
    }
    // Método para ver se há disponibilidade de viagens
    public boolean haDisponibilidade() throws ViagemRepositorioException, DadosInvalidosException, ViagemNaoAceitaException, UsuarioRepositorioException {
        return viagemServico.haDisponibilidade();
    }
}
