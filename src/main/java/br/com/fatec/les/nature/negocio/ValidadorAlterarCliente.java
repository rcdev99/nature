package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorAlterarCliente implements IStrategy {

	//Instanciando variáveis locais
	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
	
		ValidadorNome validaNome = new ValidadorNome();
		ValidadorCPF validaCPF = new ValidadorCPF();
		ValidadorRG validaRG = new ValidadorRG();
		
		if(entidadeDominio instanceof Cliente) {
			//Casting para cliente
			Cliente cliente = (Cliente) entidadeDominio;
			
			//Validando dados pessoais
			sb.append(validaNome.processar(cliente));
			sb.append(validaCPF.processar(cliente));
			sb.append(validaRG.processar(cliente));
			
			if(cliente.getDtNasc().equals(null)) {
				
				incorreto = true;
				sb.append("\nÉ necessária a inserção da data de nascimento.");
			}
			
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto || sb.toString() != null) {
				return sb.toString();
			}
			
			
		}else {
			return "Entidade não pôde ser instanciada, pois não é um cliente.";
		}
		
		
		return null;
	}

}
