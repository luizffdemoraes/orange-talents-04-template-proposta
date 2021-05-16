package br.com.zupacademy.luiz.propostas.bloqueio;

public class BloqueioExternoRequest {

	private String sistemaResponsavel;

	public BloqueioExternoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}

}
