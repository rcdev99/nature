package br.com.fatec.les.nature.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class AlterarCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidadeDominio) {
		return fachada.alterar(entidadeDominio);
	}

}
