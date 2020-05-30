package br.com.fatec.les.nature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.les.nature.model.Cartao;
import br.com.fatec.les.nature.repository.CartaoRepository;

@Repository
@Transactional
public class CartaoService {

	@Autowired
	CartaoRepository cRepository;
	
	/**
	 * Método para persistência do cartão de crédito
	 * @param cartao Cartao a ser persistido
	 */
	public void salvar(Cartao cartao) {
		
		cRepository.save(cartao);
	}
	
	/**
	 * Método para exclusão de um cartão
	 * @param cartao Cartao a ser excluído
	 */
	public void excluir(Cartao cartao) {
		
		cRepository.delete(cartao);
	}
	
	/**
	 * Método utilizado para obter os cartao cadastrados vinculados com um determinado cliente
	 * @param idCliente
	 * @return cartoes Lista de cartões contendo os cartões encontrados
	 */
	public List<Cartao> buscarCartaoPorCliente(Long idCliente) {
		
		return cRepository.findByIdClienteLike(idCliente);
		
	}
	
	/**
	 * Método utilizado para obter um cartão através de seu respectivo id
	 * @param idCartao
	 * @return
	 */
	public Cartao buscarCartaoPorId(Long idCartao) {
		
		return cRepository.findById(idCartao).get();
	}
	
	/**
	 * Método utilizados para obter todos os cartões registrados
	 * @return
	 */
	public List<Cartao> buscarTodosCartoes(){
		
		return cRepository.findAll();
		
	}
	
}
