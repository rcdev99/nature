package br.com.fatec.les.nature.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {

	private List<ItensCompra> itens;

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
	 * Método utilizado para obter a quantidade de produtos no carrinho
	 * @return
	 */
	public int getQtdProdutos() {
		
		return itens.size();
		
	}
	
}
