package negocio.basica.modelo;

import excecoes.DadosInvalidosException;

/**
 * A classe Motorista representa um motorista no sistema.
 * Ela herda da classe Pessoa e adiciona atributos e comportamentos específicos de um motorista.
 */
public class Motorista extends Pessoa {

    private String cnh;
    private Veiculo tipoVeiculo;
    private boolean disponibilidade;
    private boolean validado;

    // Construtor da classe Motorista
    public Motorista(String nome, String cpf, String telefone, String endereco, String email,
                     String cnh, Veiculo tipoVeiculo) throws DadosInvalidosException {
        super(nome, cpf, telefone, endereco, email);

        if (cnh == null || cnh.trim().isEmpty()) {
            throw new DadosInvalidosException("CNH não pode ser nula ou vazia.");
        }
        if (tipoVeiculo == null) {
            throw new DadosInvalidosException("Tipo de veículo não pode ser nulo.");
        }

        this.cnh = cnh.trim();
        this.tipoVeiculo = tipoVeiculo;
        this.disponibilidade = true; // Inicialmente, o motorista está disponível
        validado = false; // Inicialmente, o motorista não está validado
    }

    // Obtém a CNH do motorista.
    public String getCnh() {
        return cnh;
    }
    // Define a CNH do motorista.
    public void setCnh(String cnh) throws DadosInvalidosException {
        if (cnh == null || cnh.trim().isEmpty()) {
            throw new DadosInvalidosException("CNH não pode ser nula ou vazia.");
        }
        this.cnh = cnh.trim();
    }

    // Obtém o tipo de veículo do motorista.
    public Veiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    // Define o tipo de veículo do motorista.
    public void setTipoVeiculo(Veiculo tipoVeiculo) throws DadosInvalidosException {
        if (tipoVeiculo == null) {
            throw new DadosInvalidosException("Tipo de veículo não pode ser nulo.");
        }
        this.tipoVeiculo = tipoVeiculo;
    }

    // Obtém a disponibilidade do motorista.
    public boolean isDisponibilidade() {
        return disponibilidade;
    }
    // Define a disponibilidade do motorista.
    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
    // Define a disponibilidade do motorista.
    public boolean  isDisponivel() {
        return disponibilidade;
    }
    // Define a disponibilidade do motorista.
    public void emCorrida() {
        setDisponibilidade(false);
    }
    // Define a disponibilidade do motorista.
    public void finalizarCorrida() {
        setDisponibilidade(true);
    }
    // Obtém o status de validação do motorista.
    public void validarMotorista(String cnh, String cpf, Veiculo tipoVeiculo) throws DadosInvalidosException {
        if (cnh == null || cnh.trim().isEmpty()) {
            this.validado = false;
            throw new DadosInvalidosException("CNH não pode ser nula ou vazia.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            this.validado = false;
            throw new DadosInvalidosException("CPF não pode ser nulo ou vazio.");
        }
        if (tipoVeiculo == null) {
            this.validado = false;
            throw new DadosInvalidosException("Tipo de veículo não pode ser nulo.");
        }
        this.setValidado(true);
    }
    // Define o status de validação do motorista.
    public boolean isValidado(){
        return validado;
    }
    // Define o status de validação do motorista.
    public void setValidado(boolean validado) {
        this.validado = validado;
    }
}
