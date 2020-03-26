package br.com.fatec.les.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public interface ICommand {

	public Resultado execute(EntidadeDominio entidadeDominio);
	
}
