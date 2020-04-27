package br.com.fatec.les.nature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	/***
	 * Método para estabelecer conexão com o Banco de Dados PostgreSQL
	 * @return Connection Conexão válida de acesso
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost:5432/nature";
		String user = "ricardo";
		String password = "ricardo0803";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		
		return conn; 
	}
	
	public static void fechar(Connection conn){
		if(conn !=null){
			try {
				conn.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				
			}
		}
	}
	
}
