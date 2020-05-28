package br.com.fatec.les.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.CupomDesconto;

public interface CuponsRepository extends JpaRepository<CupomDesconto, Long> {

}
