package negocio;

import excecoes.DadosInvalidosException;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.PersistenciaException;
import excecoes.UsuarioRepositorioException;
import java.util.List;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Pessoa;
import negocio.basica.modelo.Veiculo;
import servico.UsuarioServico;

/**
 * Classe responsável por gerenciar as operações relacionadas aos motoristas.
 */
public class GerenciadorMotorista {

    private final UsuarioServico usuarioServico;

    public GerenciadorMotorista(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    public GerenciadorMotorista() throws UsuarioRepositorioException {
        this.usuarioServico = new UsuarioServico();
    }

    // Método para cadastrar um motorista
    public Motorista cadastrarMotorista(String nome, String cpf, String telefone, String endereco, String email, String cnh, Veiculo veiculo)
            throws DadosInvalidosException, PersistenciaException {

        if (nome == null || nome.isEmpty() ||
            cpf == null || cpf.isEmpty() ||
            telefone == null || telefone.isEmpty() ||
            endereco == null || endereco.isEmpty() ||
            email == null || email.isEmpty() ||
            cnh == null || cnh.isEmpty() ||
            veiculo == null) {
            throw new DadosInvalidosException("Todos os campos do motorista devem ser preenchidos.");
        }

        Motorista motorista = new Motorista(nome, cpf, telefone, endereco, email, cnh, veiculo);
        usuarioServico.cadastrarUsuario(motorista); // Cadastra o motorista
        return motorista;
    }

    // Método para listar todos os motoristas
    public List<Motorista> listarMotoristas() throws PersistenciaException {
        return usuarioServico.listarMotoristas(); // Retorna a lista de motoristas
    }

    // Método para buscar um motorista por ID
    public Motorista buscarMotoristaPorId(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        Pessoa usuario = usuarioServico.buscarUsuarioPorId(id); // Busca o usuário pelo ID
        if (usuario instanceof Motorista motorista) {
            return motorista;
        }
        throw new EntidadeNaoEncontradaException("Motorista não encontrado!"); // Retorna nulo se não for um motorista
    }

    // Método para buscar um motorista 
    public void atualizarMotorista(Motorista motorista)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (motorista == null) { // Verifica se o motorista é nulo
            throw new DadosInvalidosException("Motorista não pode ser nulo.");
        }
        usuarioServico.atualizarUsuario(motorista);
    }

    // Método para remover um motorista pelo ID
    public void removerMotorista(String id)
            throws DadosInvalidosException, EntidadeNaoEncontradaException, PersistenciaException {
        if (id == null || id.isEmpty()) {
            throw new DadosInvalidosException("ID do motorista não pode ser nulo ou vazio.");
        }
        usuarioServico.removerUsuario(id);
    }
}
