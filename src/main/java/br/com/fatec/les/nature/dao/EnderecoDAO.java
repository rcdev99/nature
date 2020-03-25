
package br.com.fatec.les.nature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.model.Cidade;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Estado;
import br.com.fatec.les.nature.model.Logradouro;
import br.com.fatec.les.nature.model.TipoResidencia;

public class EnderecoDAO extends AbstractJDBCDAO {

	private static final String table = "tbl_endereco";
	private static final String idTable = "end_in_id";
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//Contrutores
	public EnderecoDAO(Connection connection) {
		super(connection, table, idTable);
		
	}
	
	public EnderecoDAO() {
		super(table,idTable);
		
	}

	@Override
	public void salvar(EntidadeDominio entidadedominio) throws SQLException {
		//Abrindo conexãoo com o banco caso esteja nula
		if (connection == null) {
			openConnection();
		}
		
		//Casting para Endereco
		Endereco endereco = (Endereco) entidadedominio;
			
		try {
			//Persistindo dados do endereco
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append("(end_st_logradouro, end_in_numero, end_in_tipo_residencia, end_st_bairro, end_st_cep, end_st_cidade, end_st_estado, end_st_descricao, end_pes_in_id) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?);");
			
			//Preparando query com os atributos do objeto
			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				
				pst.setString(1, endereco.getLogradouro().getLogradouro());
				pst.setInt(2, endereco.getNumero());
				pst.setInt(3, endereco.getTipoResidencia().ordinal());
				pst.setString(4, endereco.getBairro());
				pst.setString(5, endereco.getCep());
				pst.setString(6, endereco.getCidade().getCidade());
				pst.setString(7, endereco.getCidade().getEstado().getSigla());
				pst.setString(8, endereco.getDescricao());
				pst.setInt(9, endereco.getIdPessoa());
				
				pst.executeUpdate();

				rs = pst.getGeneratedKeys();
				int idUsuario = 0;
				if(rs.next())
					idUsuario = rs.getInt(1);
				endereco.setId_endereco(idUsuario);
				
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
		Endereco endereco = (Endereco) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("(end_st_logradouro, end_in_numero, end_in_tipo_residencia, end_st_bairro, end_st_cep, end_st_cidade, end_st_estado, end_st_descricao, end_pes_in_id)");
			sql.append(" = (?,?,?,?,?,?,?,?,?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("=(?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setString(1, endereco.getLogradouro().getLogradouro());
			pst.setInt(2, endereco.getNumero());
			pst.setInt(3, endereco.getTipoResidencia().ordinal());
			pst.setString(4, endereco.getBairro());
			pst.setString(5, endereco.getCep());
			pst.setString(6, endereco.getCidade().getCidade());
			pst.setString(7, endereco.getCidade().getEstado().getSigla());
			pst.setString(8, endereco.getDescricao());
			pst.setInt(9, endereco.getIdPessoa());
			pst.setInt(10, endereco.getId_endereco());
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
		
		//Casting para Endereço
		Endereco endereco = (Endereco) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("end_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, false);
			pst.setInt(2, endereco.getId_endereco());
			
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
		
		//Casting para Endereço
		Endereco endereco = (Endereco)entidadedominio;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_endereco ON end_in_tipo_residencia = tend_in_id ");
		sql.append(" WHERE 1 = 1 ");		//Utilizado para diminuir o n�mero de IF's
		
		if(endereco.getId_endereco() != null) {
			sql.append(" AND end_in_id = ");
			sql.append(endereco.getId_endereco());
		}
		
		if(endereco.getLogradouro().getLogradouro() != null) {
			sql.append(" AND end_st_logradouro = '");
			sql.append(endereco.getLogradouro().getLogradouro());
			sql.append("'");
		}
		
		if(Integer.valueOf(endereco.getTipoResidencia().ordinal()) != null) {
			sql.append(" AND end_in_tipo_residencia = ");
			sql.append(endereco.getTipoResidencia().ordinal());
		}
		
		if(endereco.getBairro() != null) {
			sql.append(" AND end_st_bairro = '");
			sql.append(endereco.getBairro());
			sql.append("'");
		}
		
		if(endereco.getCep() != null) {
			sql.append(" AND end_st_cep = '");
			sql.append(endereco.getCep());
			sql.append("'");
		}
		
		if(endereco.getCidade().getCidade() != null) {
			sql.append(" AND end_st_cidade = '");
			sql.append(endereco.getCidade().getCidade());
			sql.append("'");
		}
		
		if(endereco.getCidade().getEstado().getSigla() != null) {
			sql.append(" AND end_st_estado = '");
			sql.append(endereco.getCidade().getEstado().getSigla());
			sql.append("'");
		}
		
		if(endereco.getIdPessoa() != null) {
			sql.append(" AND end_pes_in_id = ");
			sql.append(endereco.getIdPessoa());
		}
		
		
		try {
			
			pst = connection.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			
			List<EntidadeDominio> enderecos = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				//Instanciando objetos necessários
				endereco = new Endereco();
				Logradouro logradouro = new Logradouro();
				Cidade cidade = new Cidade();
				Estado estado = new Estado();
				
				cidade.setCidade(rs.getString("end_st_cidade"));
				estado.setSigla(rs.getString("end_st_estado"));
				
				
				TipoResidencia tipoResidencia = TipoResidencia.valueOf(rs.getString("tend_st_tipo"));
				logradouro.setLogradouro(rs.getString("end_st_logradouro"));
				cidade.setEstado(estado);
				
				endereco.setLogradouro(logradouro);
				endereco.setNumero(rs.getInt("end_in_numero"));
				endereco.setTipoResidencia(tipoResidencia);
				endereco.setBairro(rs.getString("end_st_bairro"));
				endereco.setCep(rs.getString("end_st_cep"));
				endereco.setCidade(cidade);
				endereco.setPais(rs.getString("end_st_pais"));
				endereco.setDescricao(rs.getString("end_st_descricao"));
				endereco.setIdPessoa(rs.getInt("end_pes_in_id"));
				endereco.setId_endereco(rs.getInt("end_in_id"));
						enderecos.add(endereco);
			}
			return enderecos;
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
		
		//Casting para Endereco
		Endereco endereco = (Endereco) entidadedominio;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
			sql.append("end_bo_status");
			sql.append(" = (?) ");
			sql.append("WHERE ");
			sql.append(idTable);
			sql.append("= (?)");
			
			
			pst = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO );
			
			pst.setBoolean(1, true);
			pst.setInt(2, endereco.getId_endereco());
			
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
		//Instanciando cliente
		Endereco endereco = new Endereco();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" LEFT JOIN tbl_tipo_endereco ON end_in_tipo_residencia = tend_in_id ");
		sql.append("WHERE end_pes_in_id" + " = " + idPessoa);
		
		try {
			
			pst = connection.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			
			List<EntidadeDominio> enderecos = new ArrayList<EntidadeDominio>();
			while(rs.next()) {
				
				//Instanciando objetos necessários
				endereco = new Endereco();
				Logradouro logradouro = new Logradouro();
				Cidade cidade = new Cidade();
				Estado estado = new Estado();
				
				cidade.setCidade(rs.getString("end_st_cidade"));
				estado.setSigla(rs.getString("end_st_estado"));
				
				
				TipoResidencia tipoResidencia = TipoResidencia.valueOf(rs.getString("tend_st_tipo"));
				logradouro.setLogradouro(rs.getString("end_st_logradouro"));
				cidade.setEstado(estado);
				
				endereco.setLogradouro(logradouro);
				endereco.setNumero(rs.getInt("end_in_numero"));
				endereco.setTipoResidencia(tipoResidencia);
				endereco.setBairro(rs.getString("end_st_bairro"));
				endereco.setCep(rs.getString("end_st_cep"));
				endereco.setCidade(cidade);
				endereco.setPais(rs.getString("end_st_pais"));
				endereco.setDescricao(rs.getString("end_st_descricao"));
				endereco.setIdPessoa(rs.getInt("end_pes_in_id"));
				endereco.setId_endereco(rs.getInt("end_in_id"));
						enderecos.add(endereco);
			}
			return enderecos;
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
