package negocio.basica.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A classe abstrata Pessoa representa uma entidade genérica no sistema, como um usuário ou motorista.
 * Ela contém atributos e métodos comuns a todas as pessoas no sistema.
 * Implementa a interface Serializable para permitir a serialização de objetos.
 */
public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private final String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;
    private double nota;
    private ArrayList<Avaliacao> avaliacoes;

    // Construtor padrão
    public Pessoa(String nome, String cpf, String telefone, String endereco, String email) {
        this.id = String.valueOf(idCounter++);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.nota = 0;
        this.avaliacoes = new ArrayList<>();
    }

    // obtem o id
    public String getId() {
        return id;
    }
    // Define o contador de IDs estático
    public static void setIdCounter(int idCounter) {
        Pessoa.idCounter = idCounter;
    }
    // Obtém o nome da pessoa.
    public String getNome() {
        return nome;
    }
    // Define o nome da pessoa.
    public void setNome(String nome) {
        this.nome = nome;
    }
    // Obtém o CPF da pessoa.
    public String getCpf() {
        return cpf;
    }
    // Define o CPF da pessoa.
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    // Obtém o telefone da pessoa.
    public String getTelefone() {
        return telefone;
    }
    // Define o telefone da pessoa.
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    // Obtém o endereço da pessoa.
    public String getEndereco() {
        return endereco;
    }
    // Define o endereço da pessoa.
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    // Obtém o email da pessoa.
    public String getEmail() {
        return email;
    }
    // Define o email da pessoa.
    public void setEmail(String email) {
        this.email = email;
    }
    // Obtém a nota da pessoa.
    public double getNota() {
        return nota;
    }
    // Define a nota da pessoa.
    public void setNota(double nota) {
        this.nota = nota;
    }
    // Obtém a lista de avaliações da pessoa.
    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
    // Define a lista de avaliações da pessoa.
    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
    public Avaliacao adicionarAvaliacao(double nota) {
        Avaliacao avaliacao = new Avaliacao(nota);
        this.avaliacoes.add(avaliacao);
        atualizarNotaMedia();
        return avaliacao;
    }
    // Remove uma avaliação da lista de avaliações.
    private void atualizarNotaMedia() {
        double soma = 0;
        for (Avaliacao n : avaliacoes) {
            soma += n.getNota();
        }
        this.nota = (avaliacoes.isEmpty()) ? 0 : soma / avaliacoes.size();
    }
}