package br.com.fatec.les.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.Estoque;
import br.com.fatec.les.nature.model.Produto;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
				public Estoque findByProduto(Produto produto);
				public List<Estoque> findByDisponivel(boolean status);
}
