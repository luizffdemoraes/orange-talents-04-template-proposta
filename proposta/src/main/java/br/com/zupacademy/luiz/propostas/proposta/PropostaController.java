package br.com.zupacademy.luiz.propostas.proposta;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.luiz.propostas.analise.AnalisePropostaClient;
import br.com.zupacademy.luiz.propostas.analise.AnalisePropostaRequest;
import feign.FeignException;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

	
	@Autowired private PropostaRepository propostaRepository;
	

	@Autowired private AnalisePropostaClient analisePropostaClient;
	
	
	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, 
			UriComponentsBuilder builder) {

		Proposta novaProposta = request.tranformaProposta();
		Optional<Proposta> props = propostaRepository.findByDocumento(novaProposta.getDocumento());

		//verificação de proposta duplicada
		if (props.isPresent()) {
			return ResponseEntity.status(422).body("Já existe uma proposta para este solicitante.");
		}
		
		//salvar proposta
		propostaRepository.save(novaProposta);
		logger.info("Proposta criada com sucesso!");
		var validProposta = propostaRepository.save(novaProposta);
		
		//validação da proposta
		try {
            var validacaoRequest = new AnalisePropostaRequest(validProposta);
            analisePropostaClient.analisaProposta(validacaoRequest);
            validProposta.setPropostaEstado(PropostaEstado.ELEGIVEL);
        } catch (FeignException e) {
        	validProposta.setPropostaEstado(PropostaEstado.NAO_ELEGIVEL);
        }

		URI enderecoUri = builder.path("proposta/{id}").build(novaProposta.getDocumento());
		return ResponseEntity.created(enderecoUri).build();

	}

}
