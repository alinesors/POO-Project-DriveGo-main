package dados.implementacoesArrayList;

import dados.Interface.IRepositorioCidade;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Cidade;


public class RepositorioCidadeArrayList implements IRepositorioCidade{

    private final List<Cidade> cidades;

    public RepositorioCidadeArrayList() {
        this.cidades = new ArrayList<>();
    }

    public RepositorioCidadeArrayList(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Override // Método para buscar uma cidade pelo nome
    public Cidade buscarCidade(String nome) {
        for (Cidade cidade : cidades) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null;
    }

    @Override // Método para listar todas as cidades
    public List<Cidade> listarCidades() {
        return cidades;
    }

    @Override // Método para verificar se uma cidade existe
    public void atualizarCidade(Cidade cidade) {
        for (int i = 0; i < cidades.size(); i++) {
            if (cidades.get(i).getNome().equalsIgnoreCase(cidade.getNome())) {
                cidades.set(i, cidade);
                return;
            }
        }
    }

    @Override // Método para remover uma cidade
    public void removerCidade(Cidade cidade) {
        cidades.remove(cidade);
    }

    @Override // Método para adicionar uma cidade
    public void salvarCidade(Cidade cidade) {
        cidades.add(cidade);
    }
}
