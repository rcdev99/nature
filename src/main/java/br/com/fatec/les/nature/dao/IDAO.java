package br.com.fatec.les.nature.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.fatec.les.nature.model.EntidadeDominio;

public interface IDAO {

	public void salvar(EntidadeDominio entidadedominio) throws SQLException;
	public void alterar(EntidadeDominio entidadedominio)throws SQLException;
	public void excluir(EntidadeDominio entidadedominio)throws SQLException;
	public List<EntidadeDominio> consultar(EntidadeDominio entidadedominio) throws SQLException;
	
	
}
