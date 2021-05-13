package br.com.zupacademy.luiz.propostas.cartao;

import java.time.LocalDate;
import java.util.UUID;

import br.com.zupacademy.luiz.propostas.proposta.Proposta;

public class CartoesResponse {

	private final String id;
	private final LocalDate emitidoEm;
	private final String titular;
	private final Long idProposta;

	public CartoesResponse(String id, LocalDate emitidoEm, String titular, Long idProposta) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.idProposta = idProposta;
	}

	public CartoesResponse(Cartao cartao) {
		this.id = cartao.getId();
		this.emitidoEm = cartao.getEmitidoEm();
		this.titular = cartao.getTitular();
		this.idProposta = cartao.getProposta().getId();
	}


	public String getId() {
		return id;
	}

	public LocalDate getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public Long getIdProposta() {
		return idProposta;
	}

	public Cartao toModel(Proposta proposta) {
		return new Cartao(id, emitidoEm, titular, proposta);
	}

}
