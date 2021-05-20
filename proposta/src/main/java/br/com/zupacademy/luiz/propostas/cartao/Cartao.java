package br.com.zupacademy.luiz.propostas.cartao;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import br.com.zupacademy.luiz.propostas.proposta.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(unique = true, nullable = false)
	private String numeroCartao;

	private LocalDate emitidoEm;

	private String titular;

	@Enumerated(EnumType.STRING)
	private StatusCartao status = StatusCartao.ATIVO;

	@OneToOne(mappedBy = "cartao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Proposta proposta;

	@Deprecated
	public Cartao() {
	}

	public Cartao(String numeroCartao, LocalDate emitidoEm, String titular, Proposta proposta) {
		this.numeroCartao = numeroCartao;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.proposta = proposta;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public StatusCartao getStatus() {
		return status;
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

	public String getId() {
		return id;
	}

	public void bloqueiaCartao() {
		this.status = StatusCartao.BLOQUEADO;
	}

	public boolean estaBloqueado() {
		return status.equals(StatusCartao.BLOQUEADO);
	}

}
