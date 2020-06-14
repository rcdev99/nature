package br.com.fatec.les.nature.service;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.les.nature.model.Compra;
import br.com.fatec.les.nature.repository.CompraRepository;

@Repository
@Transactional
public class CompraService {

	@Autowired
	CompraRepository cRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Método utilizado para persistir as informaçoes de uma compra
	 * @param compra Obtejo contendo os dados da compra á ser persistida
	 */
	public void salvar(Compra compra) {
		
		compra.setDataCompra(Calendar.getInstance());
		cRepository.save(compra);
	}
	
	/**
	 * Método utilizado para excluir uma determinada compra
	 * @param compra
	 */
	public void excluir(Compra compra) {
		
		cRepository.delete(compra);
	}
	
	/**
	 * Método utilizado para buscar todas as compras que contenham o id de um determinado cliente 
	 * @param idCliente Identificador único do cliente á quem as compras se referem
	 * @return Lista contendo as compras encontradas
	 */
	public List<Compra> buscarCompraPorCliente(Long idCliente) {
		
		return cRepository.findByIdClienteLike(idCliente);
	}
	
	/**
	 * Método utilziado para buscar uma compra com base em seu ID
	 * @param idCompra Identificador único referente a compra que será pesquisada
	 * @return Objeto Optional do Tipo compra, caso ela seja encontrada
	 */
	public Compra buscarCompraPorId(Long idCompra) {
		
		return cRepository.findById(idCompra).get();
	}
	
	/**
	 * Lista contendo todas as compras
	 * @return Lista do objeto Compra
	 */
	public List<Compra> buscarTodasAsCompras(){
		
		return cRepository.findAll();
	}
	
}
