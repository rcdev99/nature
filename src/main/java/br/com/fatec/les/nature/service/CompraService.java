package br.com.fatec.les.nature.service;

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
	 * Método utilizado para obter a quantidade de compras realizadas por mês durante um período
	 * @param qtdMeses Quantidade de meses do período solicitado
	 * @return List<ComprasMensalDTO> Contendo lista com a quantidade de compras realizadas
	 */
	public List<ComprasMensalDTO> obterQuantidadeComprasUltimosMeses(Integer qtdMeses){
		
		qtdMeses = trataPeriodo(qtdMeses);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * ");
		sb.append("FROM vendasultimosmeses(");
		sb.append(qtdMeses);
		sb.append(") AS ");
		sb.append("(quantidade bigint, mes double precision, ano double precision);");
		
		Query query = em.createNativeQuery(sb.toString());
		return builderComprasMensal(query.getResultList().iterator());
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Método utilizado para obter a quantidade de compras entregues por mês durante um período
	 * @param qtdMeses Quantidade de meses do periodo solicitado
	 * @return List<ComprasMensalDTO> Contendo lista com a quantidade de compras entregues
	 */
	public List<ComprasMensalDTO> obterQuantidadeEntreguesUltimosMeses(Integer qtdMeses){
		
		qtdMeses = trataPeriodo(qtdMeses);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * ");
		sb.append("FROM vendasentreguesultimosmeses(");
		sb.append(qtdMeses);
		sb.append(") AS ");
		sb.append("(quantidade bigint, mes double precision, ano double precision);");
		
		Query query = em.createNativeQuery(sb.toString());
		return builderComprasMensal(query.getResultList().iterator());
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Método utilizado para obter a quantidade de compras canceladas por mês durante um período
	 * @param qtdMeses Quantidade de meses do periodo solicitado
	 * @return List<ComprasMensalDTO> Contendo lista com a quantidade de compras canceladas
	 */
	public List<ComprasMensalDTO> obterQuantidadeCanceladasUltimosMeses(Integer qtdMeses){
		
		qtdMeses = trataPeriodo(qtdMeses);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * ");
		sb.append("FROM vendascanceladasultimosmeses(");
		sb.append(qtdMeses);
		sb.append(") AS ");
		sb.append("(quantidade bigint, mes double precision, ano double precision);");
		
		Query query = em.createNativeQuery(sb.toString());
		return builderComprasMensal(query.getResultList().iterator());
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasMensalDTO> obterQuantidadeComprasMensalEntregues(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * ");
		sb.append("FROM vw_compras_mensal_entregues ");
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
			
			int qtd = Integer.valueOf(obj[0].toString());
			int mes = Double.valueOf(obj[1].toString()).intValue();
			int ano = Double.valueOf(obj[2].toString()).intValue();
			
			//Construindo obeto de retorno
			ComprasMensalDTO qtdMensal = new ComprasMensalDTO(qtd, mes, ano);			
			comprasMensal.add(qtdMensal);
		}
		
		return comprasMensal;
	}
	
	/**
	 * Método utilizado para inverter o retorno concedido do banco de forma que a semântica de apresentação dos dados fique mais legível
	 * @param lista List<ComprasMensalDto> que terá a sequência de valor invertida
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<ComprasMensalDTO> inverterRetorno(List<ComprasMensalDTO> lista) {
		
		List<ComprasMensalDTO> inverso = new ArrayList<ComprasMensalDTO>();
				
		for(int i = (lista.size() -1); i>=0; i--) {
			ComprasMensalDTO valor = new ComprasMensalDTO();
			valor = lista.get(i);
			inverso.add(valor);
		}
		
		return inverso;
	}
	
	/**
	 * Método utilizado para tratamento do periodo de pesquisa, visando evitar problemas de exibição do gráfico
	 * @param i Integer que representa a quantidade de meses retroativos pesquisados
	 * @return int valor válido para consulta
	 */
	private int trataPeriodo(Integer i) {
		
		if(i < 2) {
			return 2;
		}
		if(i > 12 && i<24) {
			return 12;
		}
		if(i > 24) {
			return 24;
		}
		
		return i;
	}
}
