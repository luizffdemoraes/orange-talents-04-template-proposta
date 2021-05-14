package br.com.zupacademy.luiz.propostas.biometria;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

import br.com.zupacademy.luiz.propostas.cartao.Cartao;

public class BiometriaRequest {

	@NotBlank
	private String fingerprint;

	
	

	public String getFingerprint() {
		return fingerprint;
	}




	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}




	public Biometria toModel(Cartao cartao) {
		byte[] fingerprintDecoded = Base64.getDecoder().decode(this.fingerprint.getBytes());
		return new Biometria(fingerprintDecoded, cartao);

	}

}
