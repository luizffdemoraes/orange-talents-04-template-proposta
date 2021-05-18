package br.com.zupacademy.luiz.propostas.viagem;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import br.com.zupacademy.luiz.propostas.cartao.CartaoRepository;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

	private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

	@PersistenceContext
	EntityManager manager;

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ViagemRepository viagemRepository;

	@PostMapping("/{id}")
	public ResponseEntity<?> cadastrar(@PathVariable String id, @RequestBody @Valid ViagemRequest viagemRequest,
			HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent");
		String ip = request.getRemoteAddr();

		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		if (possivelCartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			return ResponseEntity.notFound().build();
		}

		Viagem viagem = viagemRequest.toModel(possivelCartao.get(), ip, userAgent);
		//manager.persist(viagem);
		viagemRepository.save(viagem);
		logger.info("Novo aviso viagem, cartão={}, destino={}, dataTermino={}", possivelCartao.get().getId(),
				viagemRequest.getDestino(), viagemRequest.getDataTermino());

		return ResponseEntity.ok().build();

	}

}
