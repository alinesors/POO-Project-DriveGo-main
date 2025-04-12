package negocio.basica.modelo;

import excecoes.DadosInvalidosException;
/**
 * A classe Origem representa o ponto de partida de uma viagem ou entrega no sistema.
 * Ela herda da classe Local, que contém os atributos e comportamentos comuns a um local.
 */
public class Origem extends Local{

    public Origem(String rua, String numero, String bairro, Cidade cidade, String estado, String cep, String complemento, String pontoReferencia) throws DadosInvalidosException {
        super(rua, numero, bairro, cidade, estado, cep, complemento, pontoReferencia);
    }

}
