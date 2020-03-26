package br.com.fatec.les.command;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ExcluirCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidadeDominio) {
		return fachada.excluir(entidadeDominio);
	}

}
