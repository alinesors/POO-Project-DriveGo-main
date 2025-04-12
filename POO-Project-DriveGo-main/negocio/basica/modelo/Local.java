package negocio.basica.modelo;

import excecoes.DadosInvalidosException;
import java.io.Serializable;

/**
 * Classe abstrata que representa um local com informações de endereço.
 * Esta classe é utilizada como base para outras classes que representam locais específicos.
 */
public abstract class Local implements Serializable {

    private String rua;
    private String numero;
    private String bairro;
    private Cidade cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String pontoReferencia;


    //Construtor da classe Local
    public Local(String rua, String numero, String bairro, Cidade cidade, String estado,
             String cep, String complemento, String pontoReferencia) throws DadosInvalidosException {

        if (rua == null || rua.trim().isEmpty()) {
            throw new DadosInvalidosException("Rua não pode ser vazia.");
        }
        if (numero == null || numero.trim().isEmpty()) {
            throw new DadosInvalidosException("Número não pode ser vazio.");
        }
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new DadosInvalidosException("Bairro não pode ser vazio.");
        }
        if (cidade == null || cidade.getNome().trim().isEmpty()) {
            throw new DadosInvalidosException("Cidade não pode ser vazia.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new DadosInvalidosException("Estado não pode ser vazio.");
        }
        if (cep == null || cep.trim().isEmpty()) {
            throw new DadosInvalidosException("CEP não pode ser vazio.");
        }

        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
        this.pontoReferencia = pontoReferencia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) throws DadosInvalidosException {
        if (rua == null || rua.trim().isEmpty()) {
            throw new DadosInvalidosException("Rua não pode ser vazia.");
        }
        this.rua = rua.trim();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws DadosInvalidosException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new DadosInvalidosException("Número não pode ser vazio.");
        }
        this.numero = numero.trim();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) throws DadosInvalidosException {
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new DadosInvalidosException("Bairro não pode ser vazio.");
        }
        this.bairro = bairro.trim();
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) throws DadosInvalidosException {
        if (cidade == null || cidade.getNome().trim().isEmpty()) {
            throw new DadosInvalidosException("Cidade não pode ser vazia.");
        }
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) throws DadosInvalidosException {
        if (estado == null || estado.trim().isEmpty()) {
            throw new DadosInvalidosException("Estado não pode ser vazio.");
        }
        this.estado = estado.trim();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) throws DadosInvalidosException {
        if (cep == null || cep.trim().isEmpty()) {
            throw new DadosInvalidosException("CEP não pode ser vazio.");
        }
        this.cep = cep.trim();
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }
}
