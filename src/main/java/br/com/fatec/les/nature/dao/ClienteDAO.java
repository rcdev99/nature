package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.fatec.les.nature.model.EntidadeDominio;

public class ClienteDAO extends AbstractJDBCDAO {

	private static final String table = "";
	private static final String idTable = "";
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//Contrutores
	public ClienteDAO(Connection connection) {
		super(connection, table, idTable);
		
	}
	
	public ClienteDAO() {
		super(table,idTable);
		
	}

	@Override
	public void salvar(EntidadeDominio entidadedominio) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(EntidadeDominio entidadedominio) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(EntidadeDominio entidadedominio) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidadedominio) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
