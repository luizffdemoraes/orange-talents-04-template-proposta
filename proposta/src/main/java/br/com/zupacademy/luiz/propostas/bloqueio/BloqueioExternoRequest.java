package br.com.zupacademy.luiz.propostas.bloqueio;


public class BloqueioExternoRequest {

	private String sistemaResponsavel;
	
	@Deprecated
	public BloqueioExternoRequest() {
	}

	public BloqueioExternoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}	

}
