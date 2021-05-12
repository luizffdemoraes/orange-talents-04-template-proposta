package br.com.zupacademy.luiz.propostas.cartao;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.zupacademy.luiz.propostas.proposta.Proposta;
import br.com.zupacademy.luiz.propostas.proposta.PropostaEstado;
import br.com.zupacademy.luiz.propostas.proposta.PropostaRepository;
import feign.FeignException;

public class AssociaCartao {

	private final Logger logger = LoggerFactory.getLogger(AssociaCartao.class);
	private final PropostaRepository propostaRepository;
	private final CartaoClient cartaoClient;

	@Autowired
	public AssociaCartao(PropostaRepository propostaRepository, CartaoClient cartaoClient) {
		this.propostaRepository = propostaRepository;
		this.cartaoClient = cartaoClient;
	}

	@Scheduled(fixedDelay = 30000)
	@Transactional
	public void associarCartao() {
		logger.info("Verificando cartões para propostas");
		List<Proposta> propostaCartao = propostaRepository.findByCartaoIsNullAndPropostaEstadoIs(PropostaEstado.ELEGIVEL)
				.parallelStream().peek(proposta -> {
					try {
						CartoesResponse cartao = cartaoClient.getCartao(proposta.getId());
						proposta.setCartao(cartao.toModel(proposta));
						logger.info("Proposta id={} atualizada com cartão", proposta.getId());
					} catch (FeignException e) {
						logger.info("Proposta id={} ainda não tem cartão", proposta.getId());
					}
				}).collect(Collectors.filtering(proposta -> proposta.getCartao() != null, Collectors.toList()));
		propostaRepository.saveAll(propostaCartao);
	}

}
