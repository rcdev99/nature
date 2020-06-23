package br.com.fatec.les.nature.service;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fatec.les.nature.model.SituacaoTroca;
import br.com.fatec.les.nature.model.Troca;
import br.com.fatec.les.nature.repository.TrocaRepository;

@Repository
@Transactional
public class TrocaService {

	@Autowired
	TrocaRepository trocaRepository;
	
	public void salvar(Troca troca) {
		
		if(troca.getDataTroca() == null) {
			troca.setDataTroca(Calendar.getInstance());
		}
		trocaRepository.save(troca);
	}
	
	public void excluir(Troca troca) {
		trocaRepository.delete(troca);
	}
	
	public Troca buscarPorId(Long id) {
		return trocaRepository.findById(id).get();
	}
	
	public List<Troca> buscarTodas() {
		return trocaRepository.findAll();
	}
	
	public Long qtdTrocasSolicitadas() {
		return trocaRepository.count();
	}
	
	public List<Troca> buscarTrocaPorStatus(boolean status){
		return trocaRepository.findByAprovada(status);
	}
	
	public List<Troca> buscarTrocaPorSituacao(SituacaoTroca situacao){
		return trocaRepository.findBySituacao(situacao);
	}
	
	
	
}
