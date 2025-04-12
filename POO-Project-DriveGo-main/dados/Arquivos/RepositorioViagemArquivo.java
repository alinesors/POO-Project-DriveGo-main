package dados.Arquivos;

import dados.Interface.IRepositorioViagem;
import excecoes.ViagemRepositorioException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Viagem;

public class RepositorioViagemArquivo implements IRepositorioViagem {

    private final String arquivo = "viagens.ser";
    private final List<Viagem> viagens;

    public RepositorioViagemArquivo() throws ViagemRepositorioException {
        this.viagens = carregarArquivo();
        atualizarIdCounter(viagens);
    }

    private void atualizarIdCounter(List<Viagem> viagens) {
        int maiorId = 0;
        for (Viagem viagem : viagens) {
            int idAtual = Integer.parseInt(viagem.getId());
            if (idAtual > maiorId) {
                maiorId = idAtual;
            }
        }
        Viagem.setIdCounter(maiorId + 1);
    }

    @Override
    public void salvarViagem(Viagem entidade) throws ViagemRepositorioException {
        viagens.add(entidade);
        salvarArquivo(viagens);
    }

    @Override
    public Viagem buscarViagem(String id) throws ViagemRepositorioException {
        for (Viagem viagem : viagens) {
            if (viagem.getId().equals(id)) {
                return viagem;
            }
        }
        return null;
    }

    @Override
    public void atualizarViagem(Viagem entidade) throws ViagemRepositorioException {
        for (int i = 0; i < viagens.size(); i++) {
            if (viagens.get(i).getId().equals(entidade.getId())) {
                viagens.set(i, entidade);
                salvarArquivo(viagens);
                return;
            }
        }
        throw new ViagemRepositorioException("Viagem com ID " + entidade.getId() + " não encontrada para atualização.");
    }

    @Override
    public void removerViagem(Viagem viagem) throws ViagemRepositorioException {
        boolean removido = viagens.removeIf(v -> v.getId().equals(viagem.getId()));
        if (!removido) {
            throw new ViagemRepositorioException("Viagem com ID " + viagem.getId() + " não encontrada para remoção.");
        }
        salvarArquivo(viagens);
    }

    @Override
    public List<Viagem> listarViagens() throws ViagemRepositorioException {
        return new ArrayList<>(viagens); // Evita que a lista original seja alterada externamente
    }

    @Override
    public List<Viagem> listarPorCliente(Cliente cliente) throws ViagemRepositorioException {
        List<Viagem> viagensPorCliente = new ArrayList<>();
        for (Viagem viagem : viagens) {
            if (viagem.getCliente() != null && viagem.getCliente().equals(cliente)) {
                viagensPorCliente.add(viagem);
            }
        }
        return viagensPorCliente;
    }

    @Override
    public List<Viagem> listarPorMotorista(Motorista motorista) throws ViagemRepositorioException {
        List<Viagem> viagensPorMotorista = new ArrayList<>();
        for (Viagem viagem : viagens) {
            if (viagem.getMotorista() != null && viagem.getMotorista().equals(motorista)) {
                viagensPorMotorista.add(viagem);
            }
        }
        return viagensPorMotorista;
    }

    private void salvarArquivo(List<Viagem> viagens) throws ViagemRepositorioException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(viagens);
        } catch (IOException e) {
            throw new ViagemRepositorioException("Erro ao salvar o arquivo de viagens.", e);
        }
    }

    private List<Viagem> carregarArquivo() throws ViagemRepositorioException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Viagem>) in.readObject();
        } catch (Exception e) {
            throw new ViagemRepositorioException("Erro ao carregar o arquivo de viagens.", e);
        }
    }

}
