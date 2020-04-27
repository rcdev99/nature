package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorNome implements IStrategy{

	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		//Verifica se entidadeDominio é um Cliente
		if(entidadeDominio instanceof Cliente) {
			
			Cliente cliente = (Cliente) entidadeDominio;
			
			//Verifica se o campo Nome foi preenchido
			if(cliente.getNome().isBlank() || cliente.getNome().isEmpty() || cliente.getNome() == null) {
				sb.append("\nPreencha corretamente o campo 'Nome'");
				incorreto = true;
			}
			//Verifica se o campo Sobrenome foi preenchido
			if(cliente.getSobrenome().isBlank() || cliente.getSobrenome().isEmpty() || cliente.getSobrenome() == null) {
				sb.append("\nPreencha corretamente o campo 'Sobrenome'");				
				incorreto = true;
			}
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto) {
				return sb.toString();
			}
			
		}else {
			return "Entidade instanciada não é um Cliente";
		}

		return null;
	}

}
