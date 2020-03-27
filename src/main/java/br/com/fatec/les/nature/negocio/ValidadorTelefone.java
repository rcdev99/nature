package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;

public class ValidadorTelefone implements IStrategy {

	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Telefone) {
			Telefone telefone = (Telefone) entidadeDominio;
			
			//Tratamendo de dados
			String ddd = telefone.getDdd();
			ddd = ddd.replaceAll("[^0-9]", "");
			telefone.setDdd(ddd);
			
			//Validações
			if(telefone.getDdd() == null || telefone.getDdd().isBlank()) {
				incorreto = true;
				sb.append("\nPreencha corretamente o Campo 'DDD'.");
			}
			
			if(telefone.getNumero() == null) {
				incorreto = true;
				sb.append("\nPreencha corretamente o Campo 'Número'.");
			}else {
				
				String numero = telefone.getNumero();
				numero = numero.replaceAll("[^0-9]", "");
				telefone.setNumero(numero);
				
				if(telefone.getNumero().isBlank()) {
				
					incorreto = true;
					sb.append("\nPreencha corretamente o Campo 'Número'. Obs.: Apenas números");
				}
				
			}
			
			if(telefone.getTipo() == null) {
				incorreto = true;
				sb.append("\nInforme o tipo de telefone.");
			}
			
			if(telefone.getIdPessoa() == null) {
				incorreto = true;
				sb.append("\nÉ necessário ter um id de Pessoa vinculado ao telefone.");
			}
			
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto) {
				return sb.toString();
			}
			
		} else {
			return "\nEntidade não pôde ser validada, pois não se trata de um Telefone.";
		}
		
		return null;
	}

}
