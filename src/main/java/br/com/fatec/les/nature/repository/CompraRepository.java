package br.com.fatec.les.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long>{
	public List<Compra> findByIdCliente(int i);
	
}
