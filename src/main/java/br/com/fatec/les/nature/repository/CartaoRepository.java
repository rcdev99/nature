package br.com.fatec.les.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{
	public List<Cartao> findByIdClienteLike(Long idCliente);
}
