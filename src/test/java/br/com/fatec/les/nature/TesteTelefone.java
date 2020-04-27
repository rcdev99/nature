package br.com.fatec.les.nature;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoTelefone;

public class TesteTelefone {
	
	public static void main(String[] args) {
		
		//Instanciando lista
		List<Telefone> telefones = new ArrayList<>();
		
		//Instancias de telefone
		Telefone telefone1 = new Telefone();
		telefone1.setDdd("11");
		telefone1.setNumero("99919-1999");
		telefone1.setTipo(TipoTelefone.CELULAR);
		
		Telefone telefone2 = new Telefone();
		telefone2.setDdd("11");
		telefone2.setNumero("4747-7474");
		telefone2.setTipo(TipoTelefone.RESIDENCIAL);

		//Adicionando telefones à Listagem
		telefones.add(telefone1);
		telefones.add(telefone2);
		
		//Exibição dos registros
		for (Telefone telefone : telefones) {
			System.out.print(telefone);
		}
		
	}
}
