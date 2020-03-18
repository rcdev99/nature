package br.com.fatec.les.nature;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.fatec.les.nature.util.Conexao;

public class TesteConexao {

	public static void main(String[] args) {
		
		try {
				Connection cx = Conexao.getConnection();
				
				if(cx == null){
					System.out.println("CONEXAO NAO ESTABELECIDA");
				}else{
					System.out.println("CONEXAO ESTABELECIDA");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		
}

