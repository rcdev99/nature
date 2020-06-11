package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carteira {

	private List<CupomDesconto> cupons;
	private List<Cartao> cartoes;
	
	public Carteira() {
		
		this.cupons = new ArrayList<CupomDesconto>();
		this.cartoes = new ArrayList<Cartao>();
	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}

	public List<CupomDesconto> getCupons() {
		return cupons;
	}

	public void setCupons(List<CupomDesconto> cupons) {
		this.cupons = cupons;
	}
	
	public void adicionarCupom(CupomDesconto cupom) {
		this.cupons.add(cupom);
	}
	
	public void removerCupom(Long idCupom) {
		
		int index = varrerListaCupons(idCupom);
				
		if(index >= 0) {
			cupons.remove(index);
		}
	}
	
	public void adicionarCartao(Cartao cartao) {
		
		cartoes.add(cartao);
	}
	
	public void removerCartao(String numCartao) {
		
		int index = varrerListaCartoes(numCartao);
		
		if(index >= 0) {
			cartoes.remove(index);
		}
		
	}
	
	public void esvaziarCarteira() {
		
		cupons.clear();
		cartoes.clear();
	}
	
	/**
	 * Método utilizado para calcular o total de descontos de uma determinada compra
	 * @return desconto BigDecimal contendo o valor total de descontos de uma compra
	 */
	public BigDecimal totalDescontos() {
		
		BigDecimal desconto = new BigDecimal(0);
		
		if(cupons != null) {
			
			for(int i= 0; i < cupons.size(); i++) {
		
				desconto = desconto.add(cupons.get(i).getValor());		
			}
		}
		
		desconto = desconto.setScale(2, RoundingMode.HALF_EVEN);
		return desconto;
	}
	
	
	/**
	 * Método responsável por realizar uma varredura na lista de cupons
	 * @return index Retorna o valor do index corrensponde ao cupoms encontrado ou -1 caso não encontre
	 */
	public int varrerListaCupons(long idProduto) {
		
		//Item já se encontra no carrinho ?
		for (int i = 0; i<cupons.size(); i++) {
			
			if(cupons.get(i).getId() == idProduto) {
				return i;
			}
			
		}
		
		return -1;
	}
	
	/**
	 * Método responsável por realizar uma varredura na lista de cupons
	 * @return index Retorna o valor do index corrensponde ao cupoms encontrado ou -1 caso não encontre
	 */
	public int varrerListaCartoes(String numCartao) {
		
		//Item já se encontra no carrinho ?
		for (int i = 0; i<cartoes.size(); i++) {
			
			if(cartoes.get(i).getNumCartao() == numCartao) {
				return i;
			}
			
		}
		
		return -1;
	}
}
