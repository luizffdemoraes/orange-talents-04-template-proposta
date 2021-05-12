package br.com.zupacademy.luiz.propostas.cartao;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartoes", url = "http://localhost:8888/api/cartoes")
public interface CartaoClient {
	
	@GetMapping("/cartoes")
	 CartoesResponse getCartao(@RequestParam("idProposta") Long idProposta);

}
