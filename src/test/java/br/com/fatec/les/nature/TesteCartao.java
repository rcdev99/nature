package br.com.fatec.les.nature;

import java.util.Calendar;

import br.com.fatec.les.nature.model.Cartao;
import br.com.fatec.les.nature.model.TipoBandeira;

public class TesteCartao {

	public static void main(String args[]) {
	
		Calendar data = Calendar.getInstance();
			
		Cartao cartao = new Cartao(1l);
			
		cartao.setTitular("Ricardo");
		cartao.setNumCartao("0000.0000.0000.0000");
		cartao.setBandeira(TipoBandeira.MASTERCARD);
		cartao.setCvv(123);
		cartao.setDataVencimento(data);
		
		System.out.println(cartao);
	}
	
}
