package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoTelefone;

public class TelefoneDAO extends AbstractJDBCDAO {
	
	private static final String table = "tbl_telefone";
	private static final String idTable = "tel_in_id";
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//Contrutores
	public TelefoneDAO(Connection connection) {
		super(connection, table, idTable);
		
	}
	
	public TelefoneDAO() {
		super(table,idTable);
		
	}
	
	@Override
	public void salvar(EntidadeDominio entidadedominio) throws SQLException {
		//Abrindo conexãoo com o banco caso esteja nula
				if (connection == null) {
					openConnection();
				}
				
				//Casting para Telefone
				Telefone telefone = (Telefone) entidadedominio;
					
				try {
					//Persistindo dados do Telefone
					connection.setAutoCommit(false);
					StringBuilder sql = new StringBuilder();
					
					sql.append("INSERT INTO ");
					sql.append(table);
					sql.append("(tel_st_ddd, tel_in_tipo, tel_st_numero, tel_pes_in_id) ");
					sql.append("VALUES (?,?,?,?);");
					
					//Preparando query com os atributos do objeto
					pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
						
						pst.setString(1, telefone.getDdd());
						pst.setInt(2, telefone.getTipo().ordinal());
						pst.setString(3, telefone.getNumero());
						pst.setInt(4, telefone.getIdPessoa());
						
						pst.executeUpdate();

						rs = pst.getGeneratedKeys();
						int idTelefone = 0;
						if(rs.next())
							idTelefone = rs.getInt(1);
						telefone.setIdTelefone(idTelefone);
						
						connection.commit();
					
				} catch (SQLException e) {
				//Tratativa de exceção
					try {
						System.out.println("Problemas ao persistir: Telefone");
						e.printStackTrace();
						connection.rollback();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				} finally {
					//Finalizando conexão
					try {
						System.out.println("Conexão para 'TelefoneDAO' será encerrada.");
						pst.close();
						connection.close();
						connection = null;
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
		
		//Casting para Telefone
		Telefone telefone = (Telefone) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("(tel_st_ddd, tel_in_tipo, tel_st_numero, tel_pes_in_id)");
			sql.append(" = (?,?,?,?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append(" = (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
		
			pst.setString(1, telefone.getDdd());
			pst.setInt(2, telefone.getTipo().ordinal());
			pst.setString(3, telefone.getNumero());
			pst.setInt(4, telefone.getIdPessoa());
			pst.setInt(5, telefone.getIdTelefone());
			pst.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			try {
				e.printStackTrace();
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
		
		//Casting para Telefone
		Telefone telefone = (Telefone) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("tel_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, false);
			pst.setInt(2, telefone.getIdTelefone());
			
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
		
		//Casting para Telefone
		Telefone telefone = (Telefone) entidadedominio;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_telefone ON tel_in_tipo = ttel_in_id ");
		sql.append(" WHERE 1 = 1 ");		//Utilizado para diminuir o número de IF's
		
		if(telefone.getIdTelefone() != null) {
			sql.append(" AND tel_in_id = ");
			sql.append(telefone.getIdTelefone());
		}
		
		if(telefone.getIdPessoa() != null) {
			sql.append(" AND tel_pes_in_id = ");
			sql.append(telefone.getIdPessoa());
		}
		
		if(telefone.getDdd() != null) {
			sql.append(" AND tel_st_ddd = '");
			sql.append(telefone.getDdd());
			sql.append("'");
		}
		
		if(telefone.getNumero() != null) {
			sql.append(" AND tel_st_numero = '");
			sql.append(telefone.getNumero());
			sql.append("'");
		}
		
		if(Integer.valueOf(telefone.getTipo().ordinal()) != null) {
			sql.append(" AND tel_in_tipo = ");
			sql.append(telefone.getTipo().ordinal());
		}
		
		
		try {
			
			pst = connection.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			
			List<EntidadeDominio> telefones = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				//Instanciando objetos necessários
				telefone = new Telefone();
				TipoTelefone tipoTelefone = TipoTelefone.valueOf(rs.getString("ttel_st_tipo")); 
				
				telefone.setDdd(rs.getString("tel_st_ddd"));
				telefone.setNumero(rs.getString("tel_st_numero"));
				telefone.setTipo(tipoTelefone);
				telefone.setIdPessoa(rs.getInt("tel_pes_in_id"));
				telefone.setIdTelefone(rs.getInt("tel_in_id"));
				
				telefones.add(telefone);
			}
			return telefones;
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
		
		//Casting para Telefone
		Telefone telefone = (Telefone) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("tel_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, true);
			pst.setInt(2, telefone.getIdTelefone());
			
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

	public List<EntidadeDominio> consultaByPessoa(int idPessoa) {
		//Abrindo conexão caso nula
		if(connection == null) {
			openConnection();
		}
		
		//Casting para Telefone
		Telefone telefone = new Telefone();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_telefone ON tel_in_tipo = ttel_in_id ");
		sql.append("WHERE tel_pes_in_id" + " = " + idPessoa);
		
		try {
			
			pst = connection.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			
			List<EntidadeDominio> telefones = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				//Instanciando objetos necessários
				telefone = new Telefone();
				TipoTelefone tipoTelefone = TipoTelefone.valueOf(rs.getString("ttel_st_tipo")); 
				
				telefone.setDdd(rs.getString("tel_st_ddd"));
				telefone.setNumero(rs.getString("tel_st_numero"));
				telefone.setTipo(tipoTelefone);
				telefone.setIdPessoa(rs.getInt("tel_pes_in_id"));
				telefone.setIdTelefone(rs.getInt("tel_in_id"));
				
				telefones.add(telefone);
			}
			return telefones;
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

}
