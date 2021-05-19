package br.com.zupacademy.luiz.propostas.viagem;

import java.util.Optional;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.luiz.propostas.bloqueio.BloqueioController;
import br.com.zupacademy.luiz.propostas.cartao.Cartao;
import br.com.zupacademy.luiz.propostas.cartao.CartaoClient;
import br.com.zupacademy.luiz.propostas.cartao.CartaoRepository;
import feign.FeignException;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

	private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

	@Autowired
	private CartaoClient client;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ViagemRepository viagemRepository;

	@PostMapping("/{id}")
	public ResponseEntity<?> cadastrar(@PathVariable Long id, @RequestBody @Valid ViagemRequest viagemRequest,
			HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent");
		String ip = request.getRemoteAddr();

		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		if (possivelCartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			return ResponseEntity.notFound().build();
		}

		try {
			client.informeViagem(possivelCartao.get().getNumeroCartao(), viagemRequest);
			Viagem avisoViagem = viagemRequest.toModel(possivelCartao.get(), ip, userAgent);
			viagemRepository.save(avisoViagem);
			logger.warn("Aviso viagem para cartão cadastrado com sucesso.");
			return ResponseEntity.ok().build();
			
		} catch (FeignException.UnprocessableEntity e) {
			logger.warn("Já existe um aviso viagem para o cartao {} com  a cidade fornecida", id);
			return ResponseEntity.unprocessableEntity().build();
		}	
//		} catch (Exception e) {
//			logger.warn("Erro ao tentar conectar com a api externa!");
//			return ResponseEntity.badRequest().build();
//		}

	}

}
