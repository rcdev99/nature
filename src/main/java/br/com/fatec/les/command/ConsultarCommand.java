package br.com.fatec.les.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ConsultarCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidadeDominio) {
		return fachada.consultar(entidadeDominio);
	}

}
