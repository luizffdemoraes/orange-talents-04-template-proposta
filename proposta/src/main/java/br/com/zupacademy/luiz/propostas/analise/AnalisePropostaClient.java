package br.com.zupacademy.luiz.propostas.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "solicitacao", url = "http://127.0.0.1:9999/api")
public interface AnalisePropostaClient {
	
	@PostMapping(value = "/solicitacao", produces = "application/json")
	public AnalisePropostaResponse analisaProposta(AnalisePropostaRequest request);
	

}



