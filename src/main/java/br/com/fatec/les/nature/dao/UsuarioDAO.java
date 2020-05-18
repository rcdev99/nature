package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.TipoUsuario;


public class UsuarioDAO extends AbstractJDBCDAO{

	private static final String table = "tbl_usuario";
	private static final String idTable = "usr_in_id";
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//Contrutores
	public UsuarioDAO(Connection connection) {
		super(connection, table, idTable);
		
	}
	
	public UsuarioDAO() {
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
					sql.append("(usr_st_email, usr_st_senha, usr_in_tipo, usr_pes_in_id) ");
					sql.append("VALUES (?,?,?,?);");
					
					//Preparando query com os atributos do objeto
					pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
						
						pst.setString(1, cliente.getEmail());
						pst.setString(2, cliente.getSenha());
						pst.setInt(3, cliente.getTipo().ordinal());
						pst.setInt(4, cliente.getId());
						pst.executeUpdate();

						rs = pst.getGeneratedKeys();
						int idUsuario = 0;
						if(rs.next())
							idUsuario = rs.getInt(1);
						cliente.setUsr_id(idUsuario);
						
						connection.commit();
					
				} catch (SQLException e) {
				//Tratativa de exceção
					try {
						System.out.println("Problemas ao persistir: Usuário");
						e.printStackTrace();
						connection.rollback();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				} finally {
					//Finalizando conexão
					try {
						System.out.println("Conexão para 'UsuárioDAO' será encerrada.");
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
		
		//Casting para Cliente
		Cliente cliente = (Cliente) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("(usr_st_email, usr_st_senha, usr_in_tipo, usr_pes_in_id)");
			sql.append(" = (?,?,?,?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("=(?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setString(1, cliente.getEmail());
			pst.setString(2, cliente.getSenha());
			pst.setInt(3, cliente.getTipo().ordinal());
			pst.setInt(4, cliente.getId());
			pst.setInt(5, cliente.getUsr_id());
			
			pst.executeUpdate();
			
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
				connection = null;
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
			sql.append("usr_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, false);
			pst.setInt(2, cliente.getUsr_id());
			
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
				connection = null;
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
		sql.append(" LEFT JOIN tbl_tipo_usuario ON  usr_in_tipo = tusr_in_id ");
		
		sql.append(" WHERE 1 = 1 ");		//Utilizado para diminuir o n�mero de IF's
		
		if(cliente.getUsr_id() != null) {
			sql.append(" AND usr_in_id = ");
			sql.append(cliente.getUsr_id());
		}
		
		if(cliente.getEmail() != null) {
			sql.append(" AND usr_st_email = '");
			sql.append(cliente.getEmail());
			sql.append("'");
		}
		
		if(cliente.getTipo() != null) {
			sql.append(" AND usr_in_tipo = ");
			sql.append(cliente.getTipo().ordinal());
		}
		
		if(cliente.getId() != null) {
			sql.append(" AND usr_pes_in_id = ");
			sql.append(cliente.getId());
		}
		
		sql.append(" AND usr_bo_status = true");
		
		try {
			
			pst = connection.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			
			List<EntidadeDominio> usuarios = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				
				cliente = new Cliente();
				TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tusr_st_acesso"));
				
				cliente.setEmail(rs.getString("usr_st_email"));
				cliente.setSenhaBD(rs.getString("usr_st_senha"));
				cliente.setTipo(tipo);
				cliente.setId(rs.getInt("usr_pes_in_id"));
				cliente.setUsr_id(rs.getInt("usr_in_id"));
				cliente.setUsr_status(rs.getBoolean("usr_bo_status"));
				
				usuarios.add(cliente);
			}
			return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				if(ctrlTransaction == true) {
					connection.close();
					connection = null;
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
			sql.append("usr_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, true);
			pst.setInt(2, cliente.getUsr_id());
			
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
				connection = null;
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public Cliente consultaById(int id) {
		//Abrindo conexão caso nula
		if(connection == null) {
			openConnection();
		}
		//Instanciando cliente
		Cliente cliente = new Cliente();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_usuario ON  usr_in_tipo = tusr_in_id ");
		sql.append("WHERE " + idTable + " = " + id);
		
		try {
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			if(rs.next()) {
				
				cliente = new Cliente();
				TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tusr_st_acesso"));
				
				
				cliente.setEmail(rs.getString("usr_st_email"));
				cliente.setSenhaBD(rs.getString("usr_st_senha"));
				cliente.setTipo(tipo);
				cliente.setId(rs.getInt("usr_pes_in_id"));
				cliente.setUsr_id(rs.getInt("usr_in_id"));
				cliente.setUsr_status(rs.getBoolean("usr_bo_status"));
				
				return cliente;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				if(ctrlTransaction == true) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Caso não encontre o cliente do id informado.
		return null;
	}

	@Bean
	public Cliente consultaByLogin(String login) {
		//Abrindo conexão caso nula
		if(connection == null) {
			openConnection();
		}
		//Instanciando cliente
		Cliente cliente = new Cliente();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_usuario ON  usr_in_tipo = tusr_in_id ");
		sql.append("WHERE usr_st_email" + " = '" + login + "'");
		
		try {
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			if(rs.next()) {
				
				cliente = new Cliente();
				TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tusr_st_acesso"));
				
				cliente.setUsr_id(rs.getInt("usr_in_id"));
				cliente.setEmail(rs.getString("usr_st_email"));
				cliente.setSenhaBD(rs.getString("usr_st_senha"));
				cliente.setTipo(tipo);
				cliente.setId(rs.getInt("usr_pes_in_id"));
				cliente.setUsr_status(rs.getBoolean("usr_bo_status"));
				
				return cliente;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Caso não encontre o cliente do id informado.
		return null;
	}
	
	public Integer getQtdUsuarios(TipoUsuario tipoUsuario) {
		//Abrindo conexão caso nula
		if(connection == null) {
			openConnection();
		}		
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("SELECT COUNT(usr_in_id)");
		sql.append(" FROM " + table + " usr");
		sql.append(" INNER JOIN tbl_pessoa pes ON usr.usr_pes_in_id = pes.pes_in_id");
		sql.append(" WHERE usr.usr_bo_status");
		sql.append(" AND pes.pes_bo_status");
		sql.append(" AND usr_in_tipo = " + tipoUsuario.ordinal());
		
		try {
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				Integer qtdUsuario;
				qtdUsuario = rs.getInt(1);

				return qtdUsuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				if(ctrlTransaction == true) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Caso não encontre nenhum resultado
		return null;
	}

}
