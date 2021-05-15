package br.com.zupacademy.luiz.propostas.cartao;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import br.com.zupacademy.luiz.propostas.proposta.Proposta;

@Entity
public class Cartao {

	@Id
	private String id;

	private LocalDate emitidoEm;

	private String titular;

	@Enumerated(EnumType.STRING)
	private StatusCartao status = StatusCartao.ATIVO;


	@OneToOne(mappedBy = "cartao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Proposta proposta;

	@Deprecated
	public Cartao() {
	}

	public Cartao(String id, LocalDate emitidoEm, String titular, Proposta proposta) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.proposta = proposta;
	}

	public String getId() {
		return id;
	}

	public LocalDate getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public void bloqueiaCartao() {
		this.status = StatusCartao.BLOQUEADO;
	}

	public boolean estaBloqueado() {
		return status.equals(StatusCartao.BLOQUEADO);
	}

}
