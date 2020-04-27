package br.com.fatec.les.nature.fachada;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;

public interface IFachada {

	public Resultado salvar(EntidadeDominio entidadeDominio);
	public Resultado alterar(EntidadeDominio entidadeDominio);
	public Resultado consultar(EntidadeDominio entidadeDominio);
	public Resultado excluir(EntidadeDominio entidadeDominio);
	
}
