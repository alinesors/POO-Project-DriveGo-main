package negocio.basica.modelo;


import java.io.Serializable;

//CLASSE AVALIACAO
//Classe que representa uma avaliação de um produto
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    private static int idCounter = 1;

    private final  String id;
    private double nota;
    private String pessoaId;
    private String viagemId;

    // Construtor da classe Avaliacao
    public Avaliacao(double nota) {
        this.id = String.valueOf(idCounter++);
        this.nota = nota;
    }

    // Construtor da classe Avaliacao sem parâmetros
    public static void setIdCounter(int idCounter) {
        Avaliacao.idCounter = idCounter;
    }
    // Construtor padrão da classe Avaliacao
    public Avaliacao() {
        this.id = String.valueOf(idCounter++);
        this.nota = 0;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public String getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(String pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getViagemId() {
        return viagemId;
    }

    public void setViagemId(String viagemId) {
        this.viagemId = viagemId;
    }
}