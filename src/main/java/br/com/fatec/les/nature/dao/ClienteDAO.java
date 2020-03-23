package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.TipoGenero;

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
			sql.append("(pes_st_nome, pes_st_sobrenome, pes_in_rg, pes_in_cpf, pes_ch_genero, pes_dt_dt_nasc) ");
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
			
		} catch (SQLException e) {
		//Tratativa de exceção
			try {
				System.out.println("Tivemos problemas de persistência");
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} finally {
			//Finalizando conexão
			try {
				System.out.println("Fechando a conexão.");
				pst.close();
				connection.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidadedominio) throws SQLException {
		if(connection == null) {
			openConnection();
		}
		
		//Casting para Cliente
		Cliente cliente = (Cliente) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			//Convertendo Data de Nascimento do cliente para o tipo Date do SQL
			Date date = Date.valueOf(cliente.getDtNasc());
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("(pes_st_nome, pes_st_sobrenome, pes_in_rg, pes_in_cpf, pes_ch_genero, pes_dt_dt_nasc)");
			sql.append(" = (?,?,?,?,?,?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("=(?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setString(1, cliente.getNome());
			pst.setString(2, cliente.getSobrenome());
			pst.setString(3, cliente.getRg());
			pst.setString(4, cliente.getCpf());
			pst.setString(5, cliente.getGenero().getDescricao());
			pst.setDate(6, date);
			pst.setInt(7, cliente.getId());
			
			pst.executeUpdate();pst.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	@Override
	public void excluir(EntidadeDominio entidadedominio) throws SQLException {
		if(connection == null) {
			openConnection();
		}
		
		//Casting para Cliente
		Cliente cliente = (Cliente) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("pes_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, false);
			pst.setInt(2, cliente.getId());
			
			pst.executeUpdate();pst.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidadedominio) throws SQLException {
		if(connection == null) {
			openConnection();
		}
		Cliente cliente = (Cliente)entidadedominio;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		
		sql.append(" WHERE 1 = 1 ");		//Utilizado para diminuir o n�mero de IF's
		
		if(cliente.getId() != null) {
			sql.append(" AND pes_in_id = ");
			sql.append(cliente.getId());
		}
		
		if(cliente.getNome() != null) {
			sql.append(" AND pes_st_nome ILIKE '%");
			sql.append(cliente.getNome());
			sql.append("%'");
		}
		
		sql.append(" ORDER BY tbl_pessoa.pes_st_nome");
		
		try {
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			
			List<EntidadeDominio> clientes = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				
				cliente = new Cliente();
				//Convertendo data recebida do Banco
				Date date = (rs.getDate("pes_dt_dt_nasc"));
				Instant instant = Instant.ofEpochMilli(date.getTime());
				LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
				
				cliente.setId(rs.getInt("pes_in_id"));
				cliente.setNome(rs.getString("pes_st_nome"));
				cliente.setSobrenome(rs.getString("pes_st_sobrenome"));
				cliente.setRg(rs.getString("pes_in_rg"));
				cliente.setCpf(rs.getString("pes_in_CPF"));
				cliente.setGenero(TipoGenero.valueOf(rs.getString("pes_ch_genero")));
				cliente.setDtNasc(localDate);
				
				clientes.add(cliente);
			}
			return clientes;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				if(ctrlTransaction == true) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		return null;
	}

	public void reativar (EntidadeDominio entidadedominio) throws SQLException {
		if(connection == null) {
			openConnection();
		}
		
		//Casting para Cliente
		Cliente cliente = (Cliente) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("pes_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, true);
			pst.setInt(2, cliente.getId());
			
			pst.executeUpdate();pst.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
