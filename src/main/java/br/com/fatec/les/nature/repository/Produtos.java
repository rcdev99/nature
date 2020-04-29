package br.com.fatec.les.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.Produto;

public interface Produtos extends JpaRepository<Produto, Long>{

}
