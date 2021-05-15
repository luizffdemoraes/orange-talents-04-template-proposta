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
import br.com.zupacademy.luiz.propostas.cartao.CartaoRepository;

@RestController
@RequestMapping("/bloqueio")
public class BloqueioController {

	private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

	private CartaoRepository cartaoRepository;
	private BloqueioRepository bloqueioRepository;

	@Autowired
	public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository) {
		this.cartaoRepository = cartaoRepository;
		this.bloqueioRepository = bloqueioRepository;
	}

	@PostMapping("/{id}")
	@Transactional
	public ResponseEntity<?> realizaBloqueio(@PathVariable String id, HttpServletRequest request) {
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		Cartao cartao = possivelCartao.get();

		if (possivelCartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			return ResponseEntity.notFound().build();
		} else if (cartao.estaBloqueado()) {
			logger.warn("Cartão já se encontrada bloqueado, id = {}", id);
			return ResponseEntity.unprocessableEntity().build();
		} else {

			String ip = request.getRemoteAddr();
			String userAgent = request.getHeader("User-Agent");
			cartao.bloqueiaCartao();
			Bloqueio bloqueio = new Bloqueio(ip, userAgent, cartao);
			bloqueioRepository.save(bloqueio);
			logger.info("Bloqueio do cartão realizado com sucesso, id = {}", id);

			return ResponseEntity.ok().build();
		}
	}
}
