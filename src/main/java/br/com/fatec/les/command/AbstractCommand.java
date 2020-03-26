package br.com.fatec.les.command;

import br.com.fatec.les.nature.fachada.Fachada;
import br.com.fatec.les.nature.fachada.IFachada;

public abstract class AbstractCommand implements ICommand {

	protected IFachada fachada = new Fachada();
	
}
