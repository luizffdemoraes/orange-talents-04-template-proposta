package br.com.zupacademy.luiz.propostas.analise;

import java.util.UUID;

import br.com.zupacademy.luiz.propostas.proposta.Proposta;

public class AnalisePropostaRequest {

	private Long idProposta;
	private String documento;
	private String nome;

	public AnalisePropostaRequest(Proposta proposta) {
		this.idProposta = proposta.getId();
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
	}

	public Long getIdProposta() {
		return idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

}
