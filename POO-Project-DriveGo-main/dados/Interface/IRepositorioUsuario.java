package dados.Interface;

import excecoes.UsuarioRepositorioException;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Pessoa;

public interface IRepositorioUsuario {

    void salvar(Pessoa entidade) throws UsuarioRepositorioException;
    
    Pessoa buscarPorId(String id) throws UsuarioRepositorioException;

    void atualizar(Pessoa entidade) throws UsuarioRepositorioException;

    void deletar(Pessoa pessoa) throws UsuarioRepositorioException;

    List<Pessoa> listarTodos() throws UsuarioRepositorioException;

    List<Cliente> listarClientes() throws UsuarioRepositorioException;

    List<Motorista> listarMotoristas() throws UsuarioRepositorioException;

    List<Motorista> listarMotoristasDisponiveis() throws UsuarioRepositorioException;

}
