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

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private BloqueioRepository bloqueioRepository;
	@Autowired
	CartaoClient client;


	@Transactional
	@PostMapping("/{id}")
	public ResponseEntity<?> realizaBloqueio(@PathVariable Long id, HttpServletRequest request) {
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		String ip = request.getRemoteAddr();
		String userAgente = request.getHeader("User-Agent");

		if (possivelCartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			return ResponseEntity.notFound().build();
		}

		Cartao cartao = possivelCartao.get();
		try {
			
			client.bloquear(cartao.getNumeroCartao(), new BloqueioExternoRequest("propostas"));
			
			Bloqueio bloqueio = new Bloqueio(ip, userAgente, cartao);
			possivelCartao.get().bloqueiaCartao();
			bloqueioRepository.save(bloqueio);
			logger.info("O cartão {} foi bloqueado", cartao.getNumeroCartao());
			return ResponseEntity.ok().build();

		} catch (FeignException.UnprocessableEntity ex) {
			logger.warn("Cartão {} já se encontrada bloqueado", cartao.getNumeroCartao());
			return ResponseEntity.unprocessableEntity().build();
		} catch (Exception e) {
			logger.warn("Erro ao tentar conectar com a api externa!");
			return ResponseEntity.badRequest().build();
		}


	}

}
