package servico;

import dados.Arquivos.RepositorioUsuarioArquivo;
import dados.Interface.IRepositorioUsuario;
import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import excecoes.UsuarioRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Pessoa;

// esse arquivo é parte do projeto de gerenciamento de usuários
public class UsuarioServico {

    private final IRepositorioUsuario repositorioUsuario;

    public UsuarioServico(IRepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public UsuarioServico() throws UsuarioRepositorioException {
        this.repositorioUsuario = new RepositorioUsuarioArquivo();
    }

    // Método para cadastrar um novo usuário
    public void cadastrarUsuario(Pessoa usuario) throws DadosInvalidosException, PersistenciaException {
        if (usuario == null || usuario.getNome() == null || usuario.getNome().isEmpty()) { // Verifica se o nome é nulo ou vazio
            throw new DadosInvalidosException("Usuário inválido. O nome é obrigatório.");
        }

        try {
            repositorioUsuario.salvar(usuario); // Salva o usuário no repositório
        } catch (UsuarioRepositorioException e) {
            throw new PersistenciaException("Erro ao cadastrar usuário.", e);
        }
    }

    // Método para buscar um usuário por ID
    public Pessoa buscarUsuarioPorId(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) { // Verifica se o ID é nulo ou vazio
            throw new DadosInvalidosException("O ID do usuário não pode ser nulo ou vazio.");
        }

        try {
            Pessoa usuario = repositorioUsuario.buscarPorId(id); // Busca o usuário pelo ID
            return usuario; // Retorna o usuário encontrado
        } catch (UsuarioRepositorioException e) { // Captura exceções específicas do repositório
            throw new PersistenciaException("Erro ao buscar usuário.", e);
        }
    }

    // Método para buscar um usuário
    public void atualizarUsuario(Pessoa usuario) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (usuario == null || usuario.getId() == null || usuario.getId().isEmpty()) { // Verifica se o ID é nulo ou vazio
            throw new DadosInvalidosException("Usuário inválido. ID obrigatório para atualização."); // Verifica se o ID é nulo ou vazio
        }

        try {
            Pessoa existente = repositorioUsuario.buscarPorId(usuario.getId()); // Busca o usuário existente pelo ID
            if (existente == null) { // Verifica se o usuário existe
                throw new EntidadeNaoEncontradaException("Usuário com ID " + usuario.getId() + " não encontrado."); // Lança exceção se não encontrado
            }

            repositorioUsuario.atualizar(usuario); // Atualiza o usuário no repositório
        } catch (UsuarioRepositorioException e) { // Captura exceções específicas do repositório
            throw new PersistenciaException("Erro ao atualizar usuário.", e);
        }
    }

    // Método para remover um usuário
    public void removerUsuario(String id) throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException { // Método para remover um usuário
        if (id == null || id.isEmpty()) {   // Verifica se o ID é nulo ou vazio
            throw new DadosInvalidosException("O ID do usuário não pode ser nulo ou vazio.");
        }

        try {
            Pessoa usuario = repositorioUsuario.buscarPorId(id); // Busca o usuário pelo ID
            if (usuario == null) {
                throw new EntidadeNaoEncontradaException("Usuário com ID " + id + " não encontrado.");
            }

            repositorioUsuario.deletar(usuario);
        } catch (UsuarioRepositorioException e) {
            throw new PersistenciaException("Erro ao remover usuário.", e);
        }
    }

    // Método para listar todos os usuários
    public List<Pessoa> listarTodosUsuarios() throws PersistenciaException {
        try {
            return repositorioUsuario.listarTodos();
        } catch (UsuarioRepositorioException e) {
            throw new PersistenciaException("Erro ao listar usuários.", e);
        }
    }

    // Método para listar todos os motoristas
    public List<Cliente> listarClientes() throws PersistenciaException {
        try {
            return repositorioUsuario.listarClientes();
        } catch (UsuarioRepositorioException e) {
            throw new PersistenciaException("Erro ao listar clientes.", e);
        }
    }
    // Método para listar todos os motoristas
    public List<Motorista> listarMotoristas() throws PersistenciaException {
        try {
            return repositorioUsuario.listarMotoristas();
        } catch (UsuarioRepositorioException e) {
            throw new PersistenciaException("Erro ao listar motoristas.", e);
        }
    }
}
