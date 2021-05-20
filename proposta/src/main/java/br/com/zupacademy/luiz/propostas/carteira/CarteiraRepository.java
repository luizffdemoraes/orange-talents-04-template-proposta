package br.com.zupacademy.luiz.propostas.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.luiz.propostas.cartao.Cartao;

public interface CarteiraRepository extends JpaRepository<Carteira, Long>{
	
	boolean existsByCarteiraAndCartao(TipoCarteira tipoCarteira, Cartao cartao);

}
