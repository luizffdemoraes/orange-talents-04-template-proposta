 package br.com.zupacademy.luiz.propostas.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.zupacademy.luiz.propostas.cartao.Cartao;
import br.com.zupacademy.luiz.propostas.cartao.CartaoClient;
import br.com.zupacademy.luiz.propostas.cartao.CartaoRepository;
import feign.FeignException;

@RestController
@RequestMapping("/bloqueio")
public class BloqueioController {

	private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

	private CartaoRepository cartaoRepository;
	private BloqueioRepository bloqueioRepository;
	private final CartaoClient cartaoClient;

	@Autowired
	public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository,
			CartaoClient cartaoClient) {
		this.cartaoRepository = cartaoRepository;
		this.bloqueioRepository = bloqueioRepository;
		this.cartaoClient = cartaoClient;
	}

	@Transactional
	@PostMapping("/{id}")
	public ResponseEntity<?> realizaBloqueio(@PathVariable String id, HttpServletRequest request) {
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		if (possivelCartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			return ResponseEntity.notFound().build();
		}

		try {
			Cartao cartao = possivelCartao.get();
			BloqueioExternoRequest bloqueioExternoRequest = new BloqueioExternoRequest("propostas");
			BloqueioExternoResponse bloqueioExternoResponse = cartaoClient.bloquear(cartao.getId(),
					bloqueioExternoRequest);
			cartao.bloqueiaCartao();
			Bloqueio bloqueio = Bloquear(request, cartao);
			bloqueioRepository.save(bloqueio);
			logger.info("Cartão bloqueado com sucesso, ID = {}", id);
		} catch (FeignException.UnprocessableEntity ex) {
			logger.warn("Cartão já se encontrada bloqueado, ID = {}", id);
			return ResponseEntity.unprocessableEntity().build();
		} catch (FeignException ex) {
			logger.warn("Houston, we have a problem!");
		}

		return ResponseEntity.ok().build();
	}

	private Bloqueio Bloquear(HttpServletRequest request, Cartao cartao) {
		String ip = request.getRemoteAddr();
		String user_Agente = request.getHeader("User-Agent");
		return new Bloqueio(ip, user_Agente, cartao);
	}

	// MUDANÇA
//	@PostMapping("/{id}")
//	@Transactional
//	public ResponseEntity<?> realizaBloqueio(@PathVariable String id, HttpServletRequest request) {
//		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
//		Cartao cartao = possivelCartao.get();
//
//		if (possivelCartao.isEmpty()) {
//			logger.warn("Cartão não encontrado.");
//			return ResponseEntity.notFound().build();
//		} else if (cartao.estaBloqueado()) {
//			logger.warn("Cartão já se encontrada bloqueado, id = {}", id);
//			return ResponseEntity.unprocessableEntity().build();
//		} else {
//
//			String ip = request.getRemoteAddr();
//			String userAgent = request.getHeader("User-Agent");
//			cartao.bloqueiaCartao();
//			Bloqueio bloqueio = new Bloqueio(ip, userAgent, cartao);
//			bloqueioRepository.save(bloqueio);
//			logger.info("Bloqueio do cartão realizado com sucesso, id = {}", id);
//
//			return ResponseEntity.ok().build();
//		}
//	}
}
