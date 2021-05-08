package br.com.zupacademy.luiz.propostas.proposta;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<Proposta> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder){
		
		Proposta novaProposta = request.tranformaProposta();
				
		URI enderecoUri = builder.path("proposta/{id}").build(novaProposta.getDocumento());
		
		propostaRepository.save(novaProposta);
		logger.info("Proposta criada com sucesso!");
		
	    return ResponseEntity.created(enderecoUri).build();
	   
		
	}
	

}
