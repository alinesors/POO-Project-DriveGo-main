package dados.implementacoesArrayList;

import dados.Interface.IRepositorioAvaliacao;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Avaliacao;

public class RepositorioAvaliacaoArrayList implements IRepositorioAvaliacao{

    private final List<Avaliacao> avaliacoes;

    public RepositorioAvaliacaoArrayList(){
        this.avaliacoes = new ArrayList<>();
    }

    @Override // Método para inserir uma avaliação
    public void inserirAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    @Override // Método para listar todas as avaliações
    public List<Avaliacao> listarAvaliacoes() {
        return avaliacoes;
    }

    @Override // Método para buscar uma avaliação pelo ID
    public Avaliacao buscarAvaliacao(String id) {
        for (Avaliacao avaliacao : avaliacoes) {
            if (String.valueOf(avaliacao.getId()).equals(id)) {
                return avaliacao;
            }
        }
        return null;
    }

    @Override // Método para atualizar uma avaliação
    public void atualizarAvaliacao(Avaliacao avaliacao) {
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getId().equals(avaliacao.getId())) {
                avaliacoes.set(i, avaliacao);
                return;
            }
        }
    }

    @Override // Método para remover uma avaliação
    public void removerAvaliacao(Avaliacao avaliacao) {
        avaliacoes.remove(avaliacao);
    }

   


}
