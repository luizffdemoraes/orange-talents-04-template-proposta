package br.com.zupacademy.luiz.propostas.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioExternoResponse {
	
	private String resultado;

    public BloqueioExternoResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

}
