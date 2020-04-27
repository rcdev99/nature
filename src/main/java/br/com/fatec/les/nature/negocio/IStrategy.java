package br.com.fatec.les.nature.negocio;

import br.com.fatec.les.nature.model.EntidadeDominio;

public interface IStrategy {

	public String processar(EntidadeDominio entidadeDominio);
	
}
