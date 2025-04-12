package dados.Arquivos;

import dados.Interface.IRepositorioUsuario;
import excecoes.UsuarioRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Pessoa;

public class RepositorioUsuarioArquivo implements IRepositorioUsuario {

    private final String arquivo = "usuarios.ser";
    private final List<Pessoa> pessoas;

    public RepositorioUsuarioArquivo() throws UsuarioRepositorioException {
        this.pessoas = carregarArquivo();
        atualizarIdCounter();
    }

    private void atualizarIdCounter() {
        int maiorId = 0;
        for (Pessoa pessoa : pessoas) {
            int idAtual = Integer.parseInt(pessoa.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        Pessoa.setIdCounter(maiorId + 1);
    }

    @Override
    public void salvar(Pessoa entidade) throws UsuarioRepositorioException {
        pessoas.add(entidade);
        salvarArquivo(pessoas);
    }

    @Override
    public Pessoa buscarPorId(String id) throws UsuarioRepositorioException {

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getId().equals(id)) {
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Pessoa entidade) throws UsuarioRepositorioException {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getId().equals(entidade.getId())) {
                pessoas.set(i, entidade);
                salvarArquivo(pessoas);
    
                return;
            }
        }
        throw new UsuarioRepositorioException("Usuário com ID " + entidade.getId() + " não encontrado para atualização.");
    }

    @Override
    public void deletar(Pessoa pessoa) throws UsuarioRepositorioException {
        boolean removido = pessoas.removeIf(p -> p.getId().equals(pessoa.getId()));
        if (!removido) {
            throw new UsuarioRepositorioException("Usuário com ID " + pessoa.getId() + " não encontrado para remoção.");
        }
        salvarArquivo(pessoas);
    }

    @Override
    public List<Pessoa> listarTodos() {
        return new ArrayList<>(pessoas);
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Cliente cliente) {
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    @Override
    public List<Motorista> listarMotoristas() {
        List<Motorista> motoristas = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Motorista motorista) {
                motoristas.add(motorista);
            }
        }
        return motoristas;
    }

    @Override
    public List<Motorista> listarMotoristasDisponiveis() throws UsuarioRepositorioException {
        List<Motorista> motoristas = new ArrayList<>();
       try{
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Motorista motorista && motorista.isDisponivel()) {
                motoristas.add(motorista);
            }
        }
        return motoristas;
       }
       catch (Exception e){
           throw new UsuarioRepositorioException("Erro ao listar motoristas disponíveis.", e);
       }
    }

    private void salvarArquivo(List<Pessoa> pessoas) throws UsuarioRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(pessoas);
        } catch (IOException e) {
            throw new UsuarioRepositorioException("Erro ao salvar usuários no arquivo.", e);
        }
    }

    private List<Pessoa> carregarArquivo() throws UsuarioRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Pessoa>) in.readObject();
        } catch (Exception e) {
            throw new UsuarioRepositorioException("Erro ao carregar o arquivo de usuários.", e);
        }
    }

}
