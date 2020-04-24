package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorRG implements IStrategy {

	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Cliente) {
			//Casting para Cliente
			Cliente cliente = (Cliente) entidadeDominio;
			
			String rg = cliente.getRg();
			//Transforma o 'x' em zero (0)
			rg = rg.replace('x', '0');
			rg = rg.replace('X', '0');
			//Retira caracteres não numéricos
			rg = rg.replaceAll("[^0-9]", "");
			cliente.setRg(rg);

			//Validações
			if(cliente.getRg().isBlank() || cliente.getRg().isEmpty() || cliente.getRg() == null) {
				incorreto = true;
				sb.append("\nPreencha o Campo 'RG'");
			}
			if(cliente.getRg().length() < 9) {
				incorreto = true;
				sb.append("\nPreencha corretamento o campo 'RG'");
			}
		
			if(incorreto) {		
				return sb.toString();
			}
		
		} else {
			return "A entidade instanciada não é um cliente.";
		}
		
		return null;
	}

}
