package br.com.zupacademy.luiz.propostas.cartao;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zupacademy.luiz.propostas.bloqueio.BloqueioExternoRequest;
import br.com.zupacademy.luiz.propostas.bloqueio.BloqueioExternoResponse;

@FeignClient(name = "cartoes", url = "http://localhost:8888/api/cartoes")
public interface CartaoClient {
	
	@GetMapping
	 CartoesResponse getCartao(@RequestParam("idProposta") Long idProposta);
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioExternoResponse bloquear(@PathVariable String id, @RequestBody BloqueioExternoRequest request);

}
