package dados.Arquivos;

import dados.Interface.IRepositorioVeiculo;
import excecoes.VeiculoRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Veiculo;

public class RepositorioVeiculoArquivo implements IRepositorioVeiculo {

    private final String arquivo = "veiculos.ser";

    public RepositorioVeiculoArquivo() throws VeiculoRepositorioException {
        List<Veiculo> veiculos = carregarArquivo();
        atualizarIdCounter(veiculos);
    }

    private void atualizarIdCounter(List<Veiculo> veiculos) {
        int maiorId = 0;
        for (Veiculo veiculo : veiculos) {
            int idAtual = Integer.parseInt(veiculo.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        Veiculo.setIdCounter(maiorId + 1);
    }

    @Override
    public void salvarVeiculo(Veiculo entidade) throws VeiculoRepositorioException {
        List<Veiculo> veiculos = listarVeiculos();
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(entidade.getPlaca())) {
                throw new VeiculoRepositorioException("Já existe um veículo cadastrado com essa placa.");
            }
        }
        veiculos.add(entidade);
        salvarArquivo(veiculos);
    }

    @Override
    public Veiculo buscarVeiculo(String id) throws VeiculoRepositorioException {
        for (Veiculo veiculo : listarVeiculos()) {
            if (veiculo.getPlaca().equalsIgnoreCase(id)) {
                return veiculo;
            }
        }
        return null;
    }

    @Override
    public void atualizarVeiculo(Veiculo entidade) throws VeiculoRepositorioException {
        List<Veiculo> veiculos = listarVeiculos();
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equalsIgnoreCase(entidade.getPlaca())) {
                veiculos.set(i, entidade);
                salvarArquivo(veiculos);
                return;
            }
        }
        throw new VeiculoRepositorioException("Veículo com placa " + entidade.getPlaca() + " não encontrado para atualização.");
    }

    @Override
    public void removerVeiculo(Veiculo veiculo) throws VeiculoRepositorioException {
        List<Veiculo> veiculos = listarVeiculos();
        boolean removido = veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(veiculo.getPlaca()));
        if (!removido) {
            throw new VeiculoRepositorioException("Veículo com placa " + veiculo.getPlaca() + " não encontrado para remoção.");
        }
        salvarArquivo(veiculos);
    }

    @Override
    public List<Veiculo> listarVeiculos() throws VeiculoRepositorioException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Veiculo>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new VeiculoRepositorioException("Erro ao listar veículos.", e);
        }
    }

    @Override
    public List<Veiculo> listarPorTipo(String tipo) throws VeiculoRepositorioException {
        List<Veiculo> veiculosPorTipo = new ArrayList<>();
        for (Veiculo veiculo : listarVeiculos()) {
            if (veiculo.getClass().getSimpleName().equalsIgnoreCase(tipo)) {
                veiculosPorTipo.add(veiculo);
            }
        }
        return veiculosPorTipo;
    }

    private void salvarArquivo(List<Veiculo> veiculos) throws VeiculoRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(veiculos);
        } catch (IOException e) {
            throw new VeiculoRepositorioException("Erro ao salvar veículos no arquivo.", e);
        }
    }

    private List<Veiculo> carregarArquivo() throws VeiculoRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Veiculo>) in.readObject();
        } catch (Exception e) {
            throw new VeiculoRepositorioException("Erro ao carregar o arquivo de veículos.", e);
        }
    }
}
