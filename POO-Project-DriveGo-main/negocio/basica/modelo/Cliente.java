package negocio.basica.modelo;

import java.util.ArrayList;
import java.util.List;

//CLASSE CLIENTE
//Classe que representa um cliente do sistema
public class Cliente extends Pessoa{

    private final List<FormaDePagamento> formasDePagamento = new ArrayList<>();

    // Construtor da classe Cliente
    public Cliente(String nome, String cpf, String telefone, String endereco, String email) {
        super(nome, cpf, telefone, endereco, email);
    }


    
    public void adicionarFormaDePagamento(FormaDePagamento forma) {
        this.formasDePagamento.add(forma);
    }
    
    public List<FormaDePagamento> getFormasDePagamento() {
        return formasDePagamento;
    }

    
    
}
