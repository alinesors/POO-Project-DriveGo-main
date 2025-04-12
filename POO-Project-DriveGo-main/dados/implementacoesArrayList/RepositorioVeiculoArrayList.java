package dados.implementacoesArrayList;

import dados.Interface.IRepositorioVeiculo;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Veiculo;

public class RepositorioVeiculoArrayList implements IRepositorioVeiculo {

    private final List<Veiculo> veiculos;

    public RepositorioVeiculoArrayList() {
        this.veiculos = new ArrayList<>();
    }

    @Override // Método para salvar um veículo
    public void salvarVeiculo(Veiculo entidade) {
        veiculos.add(entidade);
    }

    @Override // Método para buscar um veículo pelo ID (placa)
    public Veiculo buscarVeiculo(String id) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(id)) {
                return veiculo;
            }
        }
        return null;
    }

    @Override // Método para atualizar um veículo
    public void atualizarVeiculo(Veiculo entidade) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equals(entidade.getPlaca())) {
                veiculos.set(i, entidade);
                return;
            }
        }
    }

    @Override   // Método para remover um veículo
    public void removerVeiculo(Veiculo veiculo) {
        veiculos.remove(veiculo);
    }

    @Override // Método para listar todos os veículos
    public List<Veiculo> listarVeiculos() {
        return new ArrayList<>(veiculos);
    }

    @Override // Método para listar veículos por tipo
    public List<Veiculo> listarPorTipo(String tipo) {
        List<Veiculo> veiculosPorTipo = new ArrayList<>(); // Lista para armazenar veículos filtrados
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getClass().getSimpleName().equalsIgnoreCase(tipo)) { // Verifica o tipo do veículo
                veiculosPorTipo.add(veiculo);
            }
        }
        return veiculosPorTipo;
    }
}