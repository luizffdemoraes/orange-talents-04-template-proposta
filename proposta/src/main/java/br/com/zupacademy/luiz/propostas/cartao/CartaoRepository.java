package br.com.zupacademy.luiz.propostas.cartao;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

	Optional<Cartao> findByNumeroCartao(String numeroCartao);
	
	//Cartao findByNumeroCartao(String numeroCartao);
	 

}
