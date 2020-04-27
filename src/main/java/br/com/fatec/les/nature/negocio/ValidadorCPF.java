package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorCPF implements IStrategy {

	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Cliente) {
			//Casting para Cliente
			Cliente cliente = (Cliente) entidadeDominio;
			
			String cpf = cliente.getCpf();
			cpf = cpf.replaceAll("[^0-9]", "");
			cliente.setCpf(cpf);
			
			//Validações
			if(cliente.getCpf().isBlank() || cliente.getCpf().isEmpty() || cliente.getCpf() == null) {
				incorreto = true;
				sb.append("\nPreencha o Campo 'CPF'");
			}
			if(cliente.getCpf().length() != 11) {
				incorreto = true;
				sb.append("\nPreencha corretamento o campo 'CPF' OBS.: Apenas números");
			}
			
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto) {
				return sb.toString();
			}
			
		}else {
			return "Entidade instanciada não é um cliente.";
		}
		
		
		return null;
	}

}
