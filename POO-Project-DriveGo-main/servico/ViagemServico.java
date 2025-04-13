package servico;

import dados.Arquivos.RepositorioUsuarioArquivo;
import dados.Arquivos.RepositorioViagemArquivo;
import dados.Interface.IRepositorioUsuario;
import dados.Interface.IRepositorioViagem;
import excecoes.DadosInvalidosException;
import excecoes.UsuarioRepositorioException;
import excecoes.ViagemNaoAceitaException;
import excecoes.ViagemRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Viagem;

/**
 * Classe responsável por gerenciar as operações relacionadas a viagens.
 * Esta classe utiliza um repositório de viagens para realizar operações CRUD
 * e também verifica a disponibilidade de motoristas.
 */
public class ViagemServico {

    private final IRepositorioViagem repositorioViagem;
    private final IRepositorioUsuario repositorioUsuario;

    public ViagemServico(IRepositorioViagem repositorioViagem) {
        this.repositorioViagem = repositorioViagem;
        this.repositorioUsuario = null;
    }

    public ViagemServico() throws ViagemRepositorioException, UsuarioRepositorioException {
        this.repositorioViagem = new RepositorioViagemArquivo();
        this.repositorioUsuario = new RepositorioUsuarioArquivo();
    }

    // Método para cadastrar uma nova viagem
    public void cadastrarViagem(Viagem viagem) throws DadosInvalidosException, ViagemRepositorioException {
        if (viagem == null || viagem.getCliente() == null || viagem.getMotorista() == null) { // Verifica se a viagem, cliente ou motorista são nulos
            throw new DadosInvalidosException("Viagem, cliente ou motorista não podem ser nulos.");
        }
        repositorioViagem.salvarViagem(viagem); // Salva a viagem no repositório
    }
    // Método para buscar uma viagem pelo ID
    public Viagem buscarViagem(String id) throws DadosInvalidosException, ViagemRepositorioException {
        if (id == null || id.isEmpty()) { // Verifica se o ID da viagem é nulo ou vazio
            throw new DadosInvalidosException("ID da viagem não pode ser nulo ou vazio.");
        }
        Viagem viagem = repositorioViagem.buscarViagem(id); // Busca a viagem no repositório
        if (viagem == null) {
            throw new ViagemRepositorioException("Viagem com ID " + id + " não encontrada.");
        }
        return viagem; // Retorna a viagem encontrada
    }
    // Método para atualizar uma viagem existente
    public void atualizarViagem(Viagem viagem) throws DadosInvalidosException, ViagemRepositorioException {
        if (viagem == null || viagem.getId() == null || viagem.getId().isEmpty()) { // Verifica se a viagem ou o ID são nulos
            throw new DadosInvalidosException("Viagem ou ID inválido."); // Verifica se a viagem ou o ID são nulos
        }
        if (repositorioViagem.buscarViagem(viagem.getId()) == null) { // Verifica se a viagem existe no repositório
            throw new ViagemRepositorioException("Viagem com ID " + viagem.getId() + " não encontrada para atualização.");
        }
        repositorioViagem.atualizarViagem(viagem);
    }
    // Método para remover uma viagem existente
    public void removerViagem(String id) throws DadosInvalidosException, ViagemRepositorioException {
        if (id == null || id.isEmpty()) { // Verifica se o ID da viagem é nulo ou vazio
            throw new DadosInvalidosException("ID da viagem não pode ser nulo ou vazio.");
        }
        Viagem viagem = repositorioViagem.buscarViagem(id); // Busca a viagem no repositório
        if (viagem == null) { // Verifica se a viagem existe no repositório
            throw new ViagemRepositorioException("Viagem com ID " + id + " não encontrada para remoção.");
        }
        repositorioViagem.removerViagem(viagem);
    }
    // Método para listar todas as viagens
    public List<Viagem> listarViagens() throws ViagemRepositorioException { // Verifica se o repositório de viagens está vazio
        return repositorioViagem.listarViagens();
    }
    // Método para listar viagens por cliente
    public List<Viagem> listarViagensPorCliente(Cliente cliente) throws DadosInvalidosException, ViagemRepositorioException {
        if (cliente == null) { // Verifica se o cliente é nulo
            throw new DadosInvalidosException("Cliente não pode ser nulo.");
        } // Verifica se o cliente existe no repositório
        return repositorioViagem.listarPorCliente(cliente);
    }
    // Método para listar viagens por motorista
    public List<Viagem> listarViagensPorMotorista(Motorista motorista) throws DadosInvalidosException, ViagemRepositorioException {
        if (motorista == null) { // Verifica se o motorista é nulo
            throw new DadosInvalidosException("Motorista não pode ser nulo.");
        }
        return repositorioViagem.listarPorMotorista(motorista); 
    }
    // Método para listar viagens por status
    public boolean haDisponibilidade() throws DadosInvalidosException, ViagemRepositorioException, ViagemNaoAceitaException, UsuarioRepositorioException {
        List<Motorista> motoristas = repositorioUsuario.listarMotoristasDisponiveis(); // Verifica se há motoristas disponíveis
        if(motoristas.isEmpty()){
            throw new ViagemNaoAceitaException("Nenhum motorista disponível. oii");
        }
        return true; // Retorna verdadeiro se houver motoristas disponíveis
    }
}
