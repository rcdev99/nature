package br.com.fatec.les.nature.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.les.nature.dto.ComprasMensalDTO;
import br.com.fatec.les.nature.model.Compra;
import br.com.fatec.les.nature.model.SituacaoCompra;
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
	public String salvar(Compra compra) {
		
		if(compra.getItens().size() == 0 && (!compra.getSituacao().equals(SituacaoCompra.CANCELADO))) {
			return "Nenhum item foi selecionado para a compra";
		}
		
		if(compra.getIdCliente() == null) {
			return "Não foi impossível identificar o cliente";
		}
		
		if(compra.getIdEndereco() == null) {
			return "Endereço de entrega não identificado";
		}
		
		if(compra.getDataCompra()== null) {
			compra.setDataCompra(Calendar.getInstance());
		}
		
		if(compra.getSituacao() == null) {
			compra.setSituacao(SituacaoCompra.COMPRA_REALIZADA);
			cRepository.save(compra);
			
			compra.validarPagamento();
		}
		
		cRepository.save(compra);
		
		return "Compra realizada !";
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
	 * @param i Identificador único do cliente á quem as compras se referem
	 * @return Lista contendo as compras encontradas
	 */
	public List<Compra> buscarCompraPorCliente(int i) {
		
		return cRepository.findByIdCliente(i);
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
	
	/**
	 * Método utilizado para forçar a atualizar da situação de uma compra
	 */
	public void atualizarSituacaoCompras() {
		
		List<Compra> compras = cRepository.findAll();
		
		for (Compra compra : compras) {			
			if(compra.AtualizarSituaçãoCompra()) {
					cRepository.save(compra);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Método utilizado para obter a Quantidade de Compras mensais dos últimos 6 meses
	 * @return List<ComprasMensalDTO> contendo o resultado da busca ao Banco de Dados
	 */
	public List<ComprasMensalDTO> obterQuantidadeComprasMensal(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * ");
		sb.append("FROM vw_montante_compras_mensal ");
		sb.append("LIMIT 6");
		
		Query query = em.createNativeQuery(sb.toString());
		return builderComprasMensal(query.getResultList().iterator());
	}
	
	/**
	 * Método para construir uma lista de ComprasMensalDto com base no resultado de uma consulta ao Banco de Dados
	 * @param queryResult
	 * @return List<ComprasMensalDTO> Contendo os DTO´s instanciados com base na consulta realizada
	 */
	private List<ComprasMensalDTO> builderComprasMensal(Iterator<Object> queryResult){
		
		List<ComprasMensalDTO> comprasMensal = new ArrayList<ComprasMensalDTO>();
		
		while (queryResult.hasNext()) {	
			
			Object[] obj = (Object[]) queryResult.next();
			
			//Construindo obeto de retorno
			ComprasMensalDTO qtdMensal = new ComprasMensalDTO(Integer.valueOf(obj[0].toString()), //qtd de vendas
															 (Double.valueOf(obj[1].toString())).intValue(), //Mes das vendas 
															 (Double.valueOf(obj[2].toString())).intValue(), //Ano das vendas
															 BigDecimal.valueOf(Double.valueOf(obj[3].toString()))); //Valor das vendas
			
			comprasMensal.add(qtdMensal);
		}
		
		return comprasMensal;
	}
	
	
}
