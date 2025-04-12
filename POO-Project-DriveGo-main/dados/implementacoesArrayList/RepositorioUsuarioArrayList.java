package dados.implementacoesArrayList;

import dados.Interface.IRepositorioUsuario;
import excecoes.UsuarioRepositorioException;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Pessoa;

public class RepositorioUsuarioArrayList implements IRepositorioUsuario{

    private final List<Pessoa> pessoas;

    public RepositorioUsuarioArrayList() {
        this.pessoas = new ArrayList<>();
    }

    @Override // Método para salvar uma pessoa
    public void salvar(Pessoa entidade) {
        pessoas.add(entidade);
    }

    @Override // Método para buscar uma pessoa pelo ID
    public Pessoa buscarPorId(String id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getId().equals(id)) {
                return pessoa;
            }
        }
        return null; 
    }

    @Override // Método para buscar uma pessoa pelo CPF
    public void atualizar(Pessoa entidade) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getId().equals(entidade.getId())) {
                pessoas.set(i, entidade);
                return;
            }
        }
    }

    @Override // Método para deletar uma pessoa
    public void deletar(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }

    @Override // Método para buscar uma pessoa pelo CPF
    public List<Pessoa> listarTodos() {
        return pessoas;
    }

    @Override // Método para buscar uma pessoa pelo CPF
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Cliente cliente) {
                clientes.add((Cliente) cliente);
            }
        }
        return clientes;
    }

    @Override // Método para buscar um motorista pelo CPF
    public List<Motorista> listarMotoristas() {
        List<Motorista> motoristas = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Motorista motorista) {
                motoristas.add(motorista);
            }
        }
        return motoristas;
    }
 
    @Override // Método para buscar um motorista disponível
    public List<Motorista> listarMotoristasDisponiveis() throws UsuarioRepositorioException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


