package br.com.zupacademy.luiz.propostas.proposta;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/proposta")
public class PropostaController {
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder){
		
		Proposta novaProposta = request.tranformaProposta();
		Optional<Proposta> props = propostaRepository.findByDocumento(novaProposta.getDocumento());
		
		if(props.isPresent()) {
			 return ResponseEntity.status(422).body("Já existe uma proposta para este solicitante.");
		}
		
				
		URI enderecoUri = builder.path("proposta/{id}").build(novaProposta.getDocumento());
		
		propostaRepository.save(novaProposta);
		logger.info("Proposta criada com sucesso!");
		
	    return ResponseEntity.created(enderecoUri).build();
	   
		
	}
	

}
