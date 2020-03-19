package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;

public class ClienteDAO extends AbstractJDBCDAO {

	private static final String table = "tbl_pessoa";
	private static final String idTable = "pes_in_id";
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
		//Abrindo conexãoo com o banco caso esteja nula
		if (connection == null) {
			openConnection();
		}
		
		//Casting para Cliente
		Cliente cliente = (Cliente) entidadedominio;
			
		try {
			//Persistindo dados do cliente
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append("(pes_st_nome, pes_st_sobrenome, pes_in_rg, pes_in_cpf, pes_ch_genero, pes_dt_dt_nasc)");
			sql.append("VALUES (?,?,?,?,?,?);");
			
			//Convertendo Data de Nascimento do cliente para o tipo Date do SQL
			Date date = Date.valueOf(cliente.getDtNasc());
			
			//Preparando query com os atributos do objeto
			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				
				pst.setString(1, cliente.getNome());
				pst.setString(2, cliente.getSobrenome());
				pst.setString(3, cliente.getRg());
				pst.setString(4, cliente.getCpf());
				pst.setString(5, cliente.getGenero().getDescricao());
				pst.setDate(6, date);
				pst.executeUpdate();

				rs = pst.getGeneratedKeys();
				int idPessoa = 0;
				if(rs.next())
					idPessoa = rs.getInt(1);
				cliente.setId(idPessoa);
				
				connection.commit();
			
		} catch (Exception e) {
		//Tratativa de exceção
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} finally {
			//Finalizando conexão
			try {
				pst.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
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
