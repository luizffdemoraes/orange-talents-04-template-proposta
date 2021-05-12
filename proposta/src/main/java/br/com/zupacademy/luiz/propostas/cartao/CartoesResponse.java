package br.com.zupacademy.luiz.propostas.cartao;

import java.time.LocalDate;
import java.util.UUID;

import br.com.zupacademy.luiz.propostas.proposta.Proposta;

public class CartoesResponse {

	private final Long id;
	  private final LocalDate emitidoEm;
	  private final String titular;
	  private final Long idProposta;
    
	  public CartoesResponse(Cartao cartao) {
		    this.id = cartao.getId();
		    this.emitidoEm = cartao.getEmitidoEm();
		    this.titular = cartao.getTitular();
		    this.idProposta = cartao.getProposta().getId();
		  }
    
	  public Cartao toModel(Proposta proposta) {
		    return new Cartao(id, emitidoEm, titular, proposta);
		  }


    
}
