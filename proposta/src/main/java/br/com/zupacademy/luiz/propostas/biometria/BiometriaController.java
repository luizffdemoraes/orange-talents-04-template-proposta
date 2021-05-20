package br.com.zupacademy.luiz.propostas.biometria;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.luiz.propostas.cartao.Cartao;
import br.com.zupacademy.luiz.propostas.cartao.CartaoRepository;

@RestController
@RequestMapping("/biometria")
public class BiometriaController {

	
	private final CartaoRepository cartaoRepository;
	private final BiometriaRepository biometriaRepository;
	
	
	@Autowired
	public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
		this.cartaoRepository = cartaoRepository;
		this.biometriaRepository = biometriaRepository;
	}


	@PostMapping("/{id}")
	@Transactional
	public ResponseEntity<?> cadastrar(
			@PathVariable String id, 
			@RequestBody @Valid BiometriaRequest request,
			UriComponentsBuilder builder) {
		
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		if (possivelCartao.isPresent()) {
		
			Biometria biometria = request.toModel(possivelCartao.get());
			biometriaRepository.save(biometria);
			URI uri = builder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
			return ResponseEntity.created(uri).build();
			
		} else {
			return ResponseEntity.notFound().build();
			
		}

	}

}
