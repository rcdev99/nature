package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {

	private List<ItensCompra> itens;
	private BigDecimal frete;
	
	//Constructor
	public Carrinho() {
		
		this.itens = new ArrayList<ItensCompra>();
	}
	
	//Getters and Setters
	public List<ItensCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItensCompra> itens) {
		this.itens = itens;
	}
	
	/**
	 * Método responsável pela inserção de um item na lista de ItensCompra
	 * @param item Elemento a ser adicionado na lista
	 */
	public void adicionarItem(ItensCompra item) {
		
		int index;
		index = varrerLista(item.getProduto().getId());
		
		if(index < 0) {
			
			item.incrementarQtdProduto();
			itens.add(item);
		}
		
	}
	
	/**
	 * Método responsável pela remoção de um Item com base no id do prouto
	 * @param idProduto
	 */
	public void removerItem(long idProduto) {
		
		int index = varrerLista(idProduto);
		
		if(index >= 0) {
			itens.remove(index);
		}
		
	}
	
	/**
	 * Método responsável por realizar uma varredura na lista de itens
	 * @return index Retorna o valor do index corrensponde ao produto encontrado ou -1 caso não encontre
	 */
	public int varrerLista(long idProduto) {
		
		//Item já se encontra no carrinho ?
		for (int i = 0; i<itens.size(); i++) {
			
			if(itens.get(i).getProduto().getId() == idProduto) {
				return i;
			}
			
		}
		
		return -1;
	}
	
	/**
	 * Método utilizado para obtenção do valor total dos produtos inseridos no carrinho de compras.
	 * @return total BigDecimal contendo o valor total dos produtos
	 */
	public BigDecimal valorProdutos() {
		
		BigDecimal total = new BigDecimal(0);
		
		if(itens != null) {
			for (int i=0; i < itens.size() ; i++) {
				
				BigDecimal valorProduto = new BigDecimal(0);
				valorProduto = itens.get(i).getProduto().getValor();
				
				BigDecimal quantidade = new BigDecimal(0);
				quantidade = BigDecimal.valueOf(itens.get(i).getQuantidade());
	
				total = total.add((valorProduto.multiply(quantidade)));		
			}
		}
		
		total = total.setScale(2, RoundingMode.HALF_EVEN);
		return total;
	}
	
	/**
	 * Método utilizado para calcular o valor total da compra
	 * @param desconto Valor dos descontos concediso
	 * @return valor total da compra
	 */
	public BigDecimal totalCompra(BigDecimal desconto) {
		
		BigDecimal totalCompra = new BigDecimal(0);
		
		totalCompra = ((valorProdutos().add(getFrete())).subtract(desconto));
		
		if(totalCompra.compareTo(new BigDecimal(0)) <= 0){
			
			totalCompra = BigDecimal.valueOf(0);				
		}
		
		totalCompra = totalCompra.setScale(2, RoundingMode.HALF_EVEN);
		
		return totalCompra;
	}
	
	/**
	 * Método utilizado para obter a quantidade de produtos no carrinho
	 * @return
	 */
	public int getQtdProdutos() {
		
		return itens.size();
		
	}
	
	/**
	 * Método utilizado para esvaziar o carrinho
	 */
	public void limparCarrinho() {
		
		itens.clear();
	}

	/**
	 * Método para obtenção do valor de frete de uma venda
	 * @return
	 */
	public BigDecimal getFrete() {
		
		this.frete = new BigDecimal(11.87);
		BigDecimal margemFrete = new BigDecimal(50);
		
		if (valorProdutos().compareTo(margemFrete) >= 0) {
			this.frete = BigDecimal.valueOf(0);
		}
		
		this.frete = frete.setScale(2, RoundingMode.HALF_EVEN);
		
		return frete;
	}

	/**
	 * Método para inserção de um valor de frete
	 * @param frete valor do frete a ser inserido
	 */
	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}
	
}
