package br.com.zupacademy.luiz.propostas.bloqueio;


public class BloqueioRequest {

	private String ipCliente;
	private String userAgent;

	public BloqueioRequest(String ipCliente, String userAgent) {
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}



}
