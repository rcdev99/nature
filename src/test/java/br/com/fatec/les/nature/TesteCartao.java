package br.com.fatec.les.nature;

import java.time.LocalDate;

import br.com.fatec.les.nature.model.Cartao;
import br.com.fatec.les.nature.model.TipoBandeira;
import br.com.fatec.les.nature.util.FormataData;

public class TesteCartao {

	public static void main(String args[]) {
	
		FormataData formato = new FormataData();
		LocalDate data = LocalDate.parse("20/06/2020", formato.getFormato());
			
		Cartao cartao = new Cartao();
			
		cartao.setTitular("Ricardo");
		cartao.setNumCartao("0000.0000.0000.0000");
		cartao.setBandeira(TipoBandeira.MASTERCARD);
		cartao.setCvv(123);
		cartao.setDtVenc(data);
		
		System.out.println(cartao);
	}
	
}
