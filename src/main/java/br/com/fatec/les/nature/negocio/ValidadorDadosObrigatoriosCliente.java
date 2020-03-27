package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorDadosObrigatoriosCliente implements IStrategy{

	//Instanciando variáveis locais
	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	//Validadores necessários
	ValidadorNome validaNome = new ValidadorNome();
	ValidadorCPF validaCPF = new ValidadorCPF();
	ValidadorRG validaRG = new ValidadorRG();
	
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Cliente) {
			//Casting para cliente
			Cliente cliente = (Cliente) entidadeDominio;
			
			//Validando dados pessoais
			sb.append(validaNome.processar(cliente));
			sb.append(validaCPF.processar(cliente));
			sb.append(validaRG.processar(cliente));
			
			if(cliente.getGenero().equals(null)) {
				
				incorreto = true;
				sb.append("\nPreencha corretamento o campo 'Genero'.");
			}
			
			if(cliente.getDtNasc().equals(null)) {
				
				incorreto = true;
				sb.append("\nÉ necessária a inserção da data de nascimento.");
			}
			
			//Validação da lista de endereços
			if(cliente.getEnderecos().isEmpty() || cliente.getEnderecos() == null) {
				
				incorreto = true;
				sb.append("\nO cliente deve ter ao menos um endereço cadastrado.");
				
			}
			//Validação da lista de telefones
			if(cliente.getTelefones().isEmpty() || cliente.getTelefones().equals(null)) {
				
				incorreto = true;
				sb.append("\nO cliente deve ter ao menos um telefone cadastrado.");
				
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
