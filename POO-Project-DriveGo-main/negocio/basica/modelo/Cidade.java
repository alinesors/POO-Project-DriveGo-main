package negocio.basica.modelo;

import excecoes.DadosInvalidosException;
import java.io.Serializable;

// CLASSE CIDADE
// Esta classe representa uma cidade, com um nome e a opção de permitir entrega por moto.
// Ela implementa a interface Serializable para permitir a serialização de objetos desta classe.
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private final String id;
    private String nome;
    private boolean permitirMotoEntrega;


    
    public Cidade(String nome, boolean permitirMoto) throws DadosInvalidosException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("O nome da cidade não pode ser nulo ou vazio.");
        }
        this.id = String.valueOf(idCounter++);
        this.nome = nome;
        this.permitirMotoEntrega = permitirMoto;
    }

    public static void setIdCounter(int idCounter) {
        Cidade.idCounter = idCounter;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws DadosInvalidosException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("O nome da cidade não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public boolean isPermitirMoto() {
        return permitirMotoEntrega;
    }
}
