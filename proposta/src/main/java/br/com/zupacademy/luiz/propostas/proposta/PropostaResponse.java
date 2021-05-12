package br.com.zupacademy.luiz.propostas.proposta;

import java.math.BigDecimal;

import br.com.zupacademy.luiz.propostas.cartao.CartoesResponse;

public class PropostaResponse {

	  private final Long id;
	  private final CartoesResponse cartao;
	  private final String documento;
	  private final String email;
	  private final String nome;
	  private final String endereco;
	  private final BigDecimal salario;
	  private final PropostaEstado estado;
	  
	  
	public PropostaResponse(Proposta proposta) {
		this.id = proposta.getId();
		this.cartao = proposta.getCartao() != null ? new CartoesResponse(proposta.getCartao()) : null;
		this.documento = proposta.getDocumento();
		this.email = proposta.getEmail();
		this.nome = proposta.getNome();
		this.endereco = proposta.getEndereco();
		this.salario = proposta.getSalario();
		this.estado = proposta.getPropostaEstado();
	}


	public Long getId() {
		return id;
	}


	public CartoesResponse getCartao() {
		return cartao;
	}


	public String getDocumento() {
		return documento;
	}


	public String getEmail() {
		return email;
	}


	public String getNome() {
		return nome;
	}


	public String getEndereco() {
		return endereco;
	}


	public BigDecimal getSalario() {
		return salario;
	}


	public PropostaEstado getEstado() {
		return estado;
	}
	  
	  
	  
	
}
