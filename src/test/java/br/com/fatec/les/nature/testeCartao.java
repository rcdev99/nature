package br.com.fatec.les.nature;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.fatec.les.nature.model.Cartao;
import br.com.fatec.les.nature.model.TipoBandeira;

public class TesteCartao {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
	
		Date d = new Date("03/04/2020");
		Calendar data = Calendar.getInstance();
		data.setTime(d);
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		System.out.println( df.format(data.getTime()));
		
		Cartao cartao = new Cartao();
			
		cartao.setTitular("Ricardo");
		cartao.setNumCartao("0000.0000.0000.0000");
		cartao.setBandeira(TipoBandeira.MASTERCARD);
		cartao.setCvv(123);
		cartao.setDtVenc(data);
		
		System.out.println(cartao);
	}
	
}
