package br.com.fatec.les.nature.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carteira {

	private List<CupomDesconto> cupons;
	
	public Carteira() {
		
		this.cupons = new ArrayList<CupomDesconto>();
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
		
		int index = varrerLista(idCupom);
				
		if(index >= 0) {
			cupons.remove(index);
		}
	}
	
	public void esvaziarCarteira() {
		
		cupons.clear();
	}
	
	
	/**
	 * Método responsável por realizar uma varredura na lista de cupons
	 * @return index Retorna o valor do index corrensponde ao cupoms encontrado ou -1 caso não encontre
	 */
	public int varrerLista(long idProduto) {
		
		//Item já se encontra no carrinho ?
		for (int i = 0; i<cupons.size(); i++) {
			
			if(cupons.get(i).getId() == idProduto) {
				return i;
			}
			
		}
		
		return -1;
	}
}
