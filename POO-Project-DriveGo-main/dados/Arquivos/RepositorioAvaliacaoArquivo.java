package dados.Arquivos;

import dados.Interface.IRepositorioAvaliacao;
import excecoes.AvaliacaoRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Avaliacao;

public class RepositorioAvaliacaoArquivo implements IRepositorioAvaliacao {

    private final String arquivo = "avaliacoes.ser";
    private final List<Avaliacao> avaliacoes;

    public RepositorioAvaliacaoArquivo() throws AvaliacaoRepositorioException {
        this.avaliacoes = carregarArquivo();
        atualizarIdCounter(avaliacoes);
    }

    private void atualizarIdCounter(List<Avaliacao> avaliacoes) {
        int maiorId = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            int idAtual = Integer.parseInt(avaliacao.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        Avaliacao.setIdCounter(maiorId + 1);
    }

    @Override
    public void inserirAvaliacao(Avaliacao avaliacao) throws AvaliacaoRepositorioException {
        if (avaliacao == null) {
            throw new AvaliacaoRepositorioException("A avaliação não pode ser nula.");
        }
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            throw new AvaliacaoRepositorioException("A nota da avaliação deve estar entre 0 e 10.");
        }
        avaliacoes.add(avaliacao);
        salvar(avaliacoes);
    }

    @Override
    public List<Avaliacao> listarAvaliacoes() throws AvaliacaoRepositorioException {
        return new ArrayList<>(avaliacoes);
    }

    @Override
    public Avaliacao buscarAvaliacao(String id) throws AvaliacaoRepositorioException {
        if (id == null || id.isEmpty()) {
            throw new AvaliacaoRepositorioException("O ID da avaliação não pode ser nulo ou vazio.");
        }
        return avaliacoes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AvaliacaoRepositorioException("Avaliação com ID " + id + " não encontrada."));
    }

    @Override
    public void atualizarAvaliacao(Avaliacao avaliacao) throws AvaliacaoRepositorioException {
        if (avaliacao == null) {
            throw new AvaliacaoRepositorioException("A avaliação não pode ser nula.");
        }
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            throw new AvaliacaoRepositorioException("A nota da avaliação deve estar entre 0 e 10.");
        }
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getId().equals(avaliacao.getId())) {
                avaliacoes.set(i, avaliacao);
                salvar(avaliacoes);
                return;
            }
        }
        throw new AvaliacaoRepositorioException("Avaliação com ID " + avaliacao.getId() + " não encontrada para atualização.");
    }

    @Override
    public void removerAvaliacao(Avaliacao avaliacao) throws AvaliacaoRepositorioException {
        if (avaliacao == null) {
            throw new AvaliacaoRepositorioException("A avaliação não pode ser nula.");
        }
        boolean removido = avaliacoes.removeIf(a -> a.getId().equals(avaliacao.getId()));
        if (!removido) {
            throw new AvaliacaoRepositorioException("Avaliação com ID " + avaliacao.getId() + " não encontrada para remoção.");
        }
        salvar(avaliacoes);
    }

    private void salvar(List<Avaliacao> avaliacoes) throws AvaliacaoRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(avaliacoes);
        } catch (IOException e) {
            throw new AvaliacaoRepositorioException("Erro ao salvar arquivo de avaliações.", e);
        }
    }

    private List<Avaliacao> carregarArquivo() throws AvaliacaoRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Avaliacao>) in.readObject();
        } catch (Exception e) {
            throw new AvaliacaoRepositorioException("Erro ao carregar arquivo de avaliações.", e);
        }
    }
}
