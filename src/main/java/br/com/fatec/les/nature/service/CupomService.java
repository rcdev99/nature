package br.com.fatec.les.nature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.les.nature.model.CupomDesconto;
import br.com.fatec.les.nature.repository.CuponsRepository;

@Repository
@Transactional
public class CupomService {

	@Autowired
	CuponsRepository cRepository;
	
	/**
	 * Método responsável por persistir um cupom no Data Base
	 * @param cupom Objeto contendo as informações do cupom a ser persistido
	 */
	public void salvar(CupomDesconto cupom) {
		
		cRepository.save(cupom);
	}
	
	/**
	 * Método responsável pela exclusão de um cupom do Data Base
	 * @param id Identificador único do cupomque será excluído
	 */
	public void excluir(Long id) {
		
		cRepository.deleteById(id);
	}
	
	/**
	 * Método responsável por encontrar um cupom no Data Base
	 * @param id Identificador único utilizado para obter o cupom
	 * @return cupom Cupom correspondente ao id informado
	 */
	public CupomDesconto findById(long id) {
		
		CupomDesconto cupom = new CupomDesconto();
		cupom = cRepository.findById(id).get();
		
		return cupom; 
	}
	
	/**
	 * Método utilizado para localizar um Cupom de Desconto com base em seu código.
	 * @param code Código único que identifica o cupom
	 * @return O Cupom referente ao código informado
	 */
	public CupomDesconto findByCode(String code) {
		
		CupomDesconto cupom = new CupomDesconto();
		cupom = cRepository.findByCodigoLike(code);
		
		return cupom;
		
	}
	
	/**
	 * Método utilizado para obtenção de todos os cupons cadastrados
	 * @return List<CupomDesconto> Contendo todo os cupons
	 */
	public List<CupomDesconto> getAll(){
		
		List<CupomDesconto> cupons = new ArrayList<CupomDesconto>();
		
		cupons = cRepository.findAll();
		
		return cupons;
	}
}
