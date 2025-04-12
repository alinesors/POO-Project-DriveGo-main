package negocio.basica.modelo;

import excecoes.DadosInvalidosException;

//essa classe representa o destino de uma entrega
//ela herda da classe Local, que representa um local com endereço
public class Destino extends Local{

    public Destino(String rua, String numero, String bairro, Cidade cidade, String estado, String cep, String complemento, String pontoReferencia) throws DadosInvalidosException {
        super(rua, numero, bairro, cidade, estado, cep, complemento, pontoReferencia);
    }
}
