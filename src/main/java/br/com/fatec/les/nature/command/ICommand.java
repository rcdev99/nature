package br.com.fatec.les.nature.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public interface ICommand {

	public Resultado execute(EntidadeDominio entidadeDominio);
	
}
