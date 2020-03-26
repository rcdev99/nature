package br.com.fatec.les.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class SalvarCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidadeDominio) {
		return fachada.salvar(entidadeDominio);
	}
	
}
