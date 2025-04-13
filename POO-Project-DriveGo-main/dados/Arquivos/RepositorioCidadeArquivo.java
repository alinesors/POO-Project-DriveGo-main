package dados.Arquivos;

import dados.Interface.IRepositorioCidade;
import excecoes.CidadeRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Cidade;

public class RepositorioCidadeArquivo implements IRepositorioCidade {

    private final String arquivo = "cidades.ser";
    private List<Cidade> cidades;

    public RepositorioCidadeArquivo() throws CidadeRepositorioException {
        cidades = carregarArquivo();
        atualizarIdCounter(cidades);
    }

    private void atualizarIdCounter(List<Cidade> cidades) {
        int maiorId = 0;
        for (Cidade cidade : cidades) {
            int idAtual = Integer.parseInt(cidade.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        Cidade.setIdCounter(maiorId + 1); // Atualiza o contador para o próximo ID disponível
    }

    @Override
    public void salvarCidade(Cidade cidade) throws CidadeRepositorioException {
        cidades = listarCidades();
        for (Cidade c : cidades) {
            if (c.getNome().equalsIgnoreCase(cidade.getNome())) {
                throw new CidadeRepositorioException("Cidade já cadastrada com esse nome.");
            }
        }
        cidades.add(cidade);
        salvar(cidades);
    }

    @Override
    public Cidade buscarCidade(String id) throws CidadeRepositorioException {
        for (Cidade cidade : listarCidades()) {
            if (cidade.getId().equalsIgnoreCase(id)) {
                return cidade;
            }
        }
        return null;
    }

    @Override
    public void atualizarCidade(Cidade cidade) throws CidadeRepositorioException {
        cidades = listarCidades();
        for (int i = 0; i < cidades.size(); i++) {
            if (cidades.get(i).getId().equalsIgnoreCase(cidade.getId())) {
                cidades.set(i, cidade);
                salvar(cidades);
    
                return;
            }
        }
        throw new CidadeRepositorioException("Cidade não encontrada para atualização.");
    }

    @Override
    public void removerCidade(Cidade cidade) throws CidadeRepositorioException {
        cidades = listarCidades();
        boolean removido = cidades.removeIf(c -> c.getNome().equalsIgnoreCase(cidade.getNome()));
        if (!removido) {
            throw new CidadeRepositorioException("Cidade não encontrada para remoção.");
        }
        salvar(cidades);
    }

    @Override
    public List<Cidade> listarCidades() throws CidadeRepositorioException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Cidade>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new CidadeRepositorioException("Erro ao listar cidades.", e);
        }
    }

    private void salvar(List<Cidade> cidades) throws CidadeRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(cidades);
        } catch (IOException e) {
            throw new CidadeRepositorioException("Erro ao salvar cidades.", e);
        }
    }

    private List<Cidade> carregarArquivo() throws CidadeRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Cidade>) in.readObject();
        } catch (Exception e) {
            throw new CidadeRepositorioException("Erro ao carregar o arquivo de cidades.", e);
        }
    }

    
}
