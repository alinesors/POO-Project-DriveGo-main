package dados.implementacoesArrayList;
import dados.Interface.IRepositorioViagem;
import java.util.List;
import negocio.basica.modelo.Cliente;
import negocio.basica.modelo.Motorista;
import negocio.basica.modelo.Viagem;


public class RepositorioViagemArrayList implements IRepositorioViagem{

    private List<Viagem> viagens;

    @Override
    public void salvarViagem(Viagem entidade) {
        viagens.add(entidade);
    }

    @Override
    public Viagem buscarViagem(String id) {
        for (Viagem viagem : viagens) {
            if (String.valueOf(viagem.getId()).equals(id)) {
                return viagem;
            }
        }
        return null;  
    }

    @Override // Método para atualizar uma viagem
    public void atualizarViagem(Viagem entidade) {
        for (int i = 0; i < viagens.size(); i++) {
            if (viagens.get(i).getId().equals(entidade.getId())) {
                viagens.set(i, entidade);
                return;
            }
        }
       
    }

    @Override // Método para remover uma viagem
    public void removerViagem(Viagem viagem) {
        viagens.remove(viagem);
    }

    @Override 
    public List<Viagem> listarViagens() {
        return viagens;
        
    }

    @Override // Método para listar viagens por cliente
    public List<Viagem> listarPorCliente(Cliente cliente) {
        for (Viagem viagem : viagens) {
            if (viagem.getCliente().equals(cliente)) {
                return viagens;
            }
        }
        return null;

        
    }

    @Override // Método para listar viagens por motorista
    public List<Viagem> listarPorMotorista(Motorista motorista) {
        for (Viagem viagem : viagens) {
            if (viagem.getMotorista().equals(motorista)) {
                return viagens;
            }
        }
        return null;
        
    }



}
