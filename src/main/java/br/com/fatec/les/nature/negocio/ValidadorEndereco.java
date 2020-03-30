package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ValidadorEndereco implements IStrategy {

	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Endereco) {
			
			Endereco endereco = (Endereco) entidadeDominio;
			
			//Validando dados do endereço
			if(endereco.getLogradouro().equals(null) || endereco.getLogradouro().getLogradouro().isBlank()) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Logradouro'.");
			}
			
			if(endereco.getNumero().equals(null) || endereco.getNumero() < 0) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Número'.");
			}
			
			if(endereco.getBairro().equals(null) || endereco.getBairro().isBlank()) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Número'.");				
			}
			
			if(!endereco.getCep().equals(null) || !endereco.getCep().isBlank()) {
				
				String cep = endereco.getCep();
				cep = cep.replaceAll("[^0-9]", "");
				endereco.setCep(cep);
				
				if(endereco.getCep().length() != 8) {
					incorreto = true;
					sb.append("\nPreencha corretamente o campo 'CEP'.");
				}
				
			}else {
				
				incorreto = true;
				sb.append("\nO Campo 'CEP' deve ser preenchido.");
			}
			
			if(endereco.getCidade().equals(null) || endereco.getCidade().getCidade().isBlank()) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Cidade'.");
			}
			
			if(endereco.getCidade().getEstado().equals(null) || endereco.getCidade().getEstado().getSigla().isBlank() || endereco.getCidade().getEstado().getSigla().equals(null)) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Estado'.");
			}
			
			if(endereco.getDescricao().equals(null) || endereco.getDescricao().isBlank()) {
				incorreto = true;
				sb.append("\nPreencha corretamente o campo 'Descrição'.");				
			}
			
			if(endereco.getIdPessoa() == null) {
				incorreto = true;
				sb.append("\nO endereço deve estar vinculado á uma pessoa.");
			}else if(endereco.getIdPessoa().toString().isBlank() || endereco.getIdPessoa() < 0) {
				incorreto = true;
				sb.append("\nId de vínculo com Pessoa inválido.");
			}
			
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto) {
				return sb.toString();
			}
			
		}else {
			return "\nImpossível validar, pois entidade não é um endereço.";
		}
		
		return null;
	}

}
