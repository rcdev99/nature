package br.com.fatec.les.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.nature.model.SituacaoTroca;
import br.com.fatec.les.nature.model.Troca;

public interface TrocaRepository extends JpaRepository<Troca, Long>{
		public List<Troca>findBySituacao(SituacaoTroca situacao);
		public List<Troca>findByAprovada(boolean status);
}
