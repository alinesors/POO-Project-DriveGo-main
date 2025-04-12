package negocio;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import excecoes.UsuarioRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Pessoa;
import servico.UsuarioServico;

/**
 * Classe responsável por gerenciar as operações relacionadas a clientes.
 * Ela utiliza o serviço de usuário para realizar as operações de persistência.
 */
public class GerenciadorCliente {

    private final UsuarioServico usuarioServico;

    public GerenciadorCliente(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    public GerenciadorCliente() throws UsuarioRepositorioException {
        this.usuarioServico = new UsuarioServico();
    }

    // Método para cadastrar um novo cliente
    public Cliente cadastrarCliente(String nome, String cpf, String telefone, String endereco, String email)
            throws DadosInvalidosException, PersistenciaException {

        if (nome == null || nome.isEmpty() ||
            cpf == null || cpf.isEmpty() ||
            telefone == null || telefone.isEmpty() ||
            endereco == null || endereco.isEmpty() ||
            email == null || email.isEmpty()) {
            throw new DadosInvalidosException("Todos os campos do cliente devem ser preenchidos.");
        }

        Cliente cliente = new Cliente(nome, cpf, telefone, endereco, email); // Cria um novo cliente
        usuarioServico.cadastrarUsuario(cliente);
        return cliente;
    }
    // Método para listar todos os clientes
    public List<Cliente> listarClientes() throws PersistenciaException {
        return usuarioServico.listarClientes();
    }
    // Método para buscar um cliente por ID
    public Cliente buscarClientePorId(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        Pessoa usuario = usuarioServico.buscarUsuarioPorId(id);
        if (usuario instanceof Cliente cliente) {
            return cliente;
        }
        return null;
    }
    // Método para buscar um cliente
    public void atualizarCliente(Cliente cliente)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo.");
        }
        usuarioServico.atualizarUsuario(cliente);
    }
    // Método para remover um cliente
    public void removerCliente(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("ID do cliente não pode ser nulo ou vazio.");
        }
        usuarioServico.removerUsuario(id);
    }


}
