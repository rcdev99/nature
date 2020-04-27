package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.fatec.les.nature.util.Conexao;

public abstract class AbstractJDBCDAO implements IDAO {

	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction = true;
	
	public AbstractJDBCDAO(Connection connection, String table, String idTable) {
		this.connection = connection;
		this.table = table;
		this.idTable = idTable;
	}
	
	public AbstractJDBCDAO(String table, String idTable) {
		this.table = table;
		this.idTable = idTable;
	}
	
	protected void openConnection(){
		try {
			
			if(connection == null || connection.isClosed())
				connection = Conexao.getConnection();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
