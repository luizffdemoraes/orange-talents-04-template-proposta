package br.com.zupacademy.luiz.propostas.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import br.com.zupacademy.luiz.propostas.cartao.Cartao;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	private LocalDateTime instanteBloqueio;

	@NotBlank
	private String ip;

	@NotBlank
	private String userAgent;

	//@OneToOne(cascade = CascadeType.MERGE)
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cartao cartao;

	public Bloqueio() {
	}

	public Bloqueio(String ip, String userAgent, Cartao cartao) {
		this.ip = ip;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}

	

}
