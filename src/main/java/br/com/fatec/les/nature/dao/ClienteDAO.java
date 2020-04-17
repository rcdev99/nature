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
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoGenero;
import br.com.fatec.les.nature.model.TipoUsuario;
import br.com.fatec.les.nature.model.Usuario;

public class ClienteDAO extends AbstractJDBCDAO {

	private static final String table = "tbl_pessoa";
	private static final String idTable = "pes_in_id";
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//DAO´s complementares
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	EnderecoDAO DAOEndereco = new EnderecoDAO();
	TelefoneDAO DAOTelefone = new TelefoneDAO();
	
	//Listas necessárias
	List <EntidadeDominio> usuarios = new ArrayList<EntidadeDominio>();
	List <EntidadeDominio> telefones = new ArrayList<EntidadeDominio>();
	List <EntidadeDominio> enderecos = new ArrayList<EntidadeDominio>();
	
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
				
				//Intancia de lista para enderecoes e telefones
				List<Endereco> enderecos = new ArrayList<Endereco>();
				List<Telefone> telefones = new ArrayList<Telefone>();
				
				//Obtendo enderecos e telefones do cliente
				enderecos = cliente.getEnderecos();
				telefones = cliente.getTelefones();
				
				//Persistindo dados complementares
				DAOUsuario.salvar(cliente);
				
				if(enderecos.size() > 0) {
					for (Endereco endereco: enderecos) {
						endereco.setIdPessoa(cliente.getId());
						DAOEndereco.salvar(endereco);
					}
				}
				if(telefones.size() > 0) {
					for (Telefone telefone: telefones) {
						telefone.setIdPessoa(cliente.getId());
						DAOTelefone.salvar(telefone);
					}
				}
				
			
		} catch (SQLException e) {
		//Tratativa de exceção
			try {
				System.out.println("Problemas ao persistir: Cliente");
				e.printStackTrace();
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} finally {
			//Finalizando conexão
			try {
				System.out.println("Conexão para 'ClienteDAO' será encerrada.");
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
		
		sql.append(" AND pes_bo_status = true");
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
				
				//Consultando dados de usuario do cliente
				if(cliente.getId() != null) {
					usuarios = DAOUsuario.consultar(cliente);
					telefones = DAOTelefone.consultaByPessoa(cliente.getId());
					enderecos = DAOEndereco.consultaByPessoa(cliente.getId());
				}
				
				//Obtendo dados de usuario do cliente
				if(usuarios.size() > 0) {
					Usuario user = new Cliente();
					user = (Usuario) usuarios.get(0);
					
					cliente.setEmail(user.getEmail());
					cliente.setSenhaBD(user.getSenha());
					cliente.setTipo(user.getTipo());
					cliente.setUsr_id(user.getUsr_id());
				}
				
				if(telefones.size() > 0) {
					//Adicionando telefones ao cliente
					for (EntidadeDominio telefone : telefones) {
					
						Telefone tel = new Telefone();
						tel = (Telefone) telefone;
						cliente.addTelefone(tel);						
					}
				}
				
				if(enderecos.size() > 0) {
					//Adicionando endereços ao cliente
					for (EntidadeDominio endereco : enderecos) {
					
						Endereco end = new Endereco();
						end = (Endereco) endereco;
						cliente.addEndereco(end);						
					}
				}
				
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
		sql.append(" WHERE " + idTable + " = " + id);
		sql.append(" AND pes_bo_status = true");
		
		try {
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			if(rs.next()) {	
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
				
				//Consultando dados de usuario do cliente
				if(cliente.getId() != null) {
					usuarios = DAOUsuario.consultar(cliente);
					telefones = DAOTelefone.consultaByPessoa(cliente.getId());
					enderecos = DAOEndereco.consultaByPessoa(cliente.getId());
				}
				
				//Obtendo dados de usuario do cliente
				if(usuarios.size() > 0) {
					Usuario user = new Cliente();
					user = (Usuario) usuarios.get(0);
					
					cliente.setEmail(user.getEmail());
					cliente.setSenhaBD(user.getSenha());
					cliente.setTipo(user.getTipo());
					cliente.setUsr_id(user.getUsr_id());
				}
				//Houve retorno de número(s) de telefone(s) ? 
				if(telefones.size() > 0) {
					//Adicionando telefones ao cliente
					for (EntidadeDominio telefone : telefones) {
					
						Telefone tel = new Telefone();
						tel = (Telefone) telefone;
						cliente.addTelefone(tel);						
					}
				}
				//Houve retorno de endereço(s) ?
				if(enderecos.size() > 0) {
					//Adicionando endereços ao cliente
					for (EntidadeDominio endereco : enderecos) {
					
						Endereco end = new Endereco();
						end = (Endereco) endereco;
						cliente.addEndereco(end);						
					}
				}
				
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
	
	public List<Cliente> getClientes(){
		
			//Abrindo conexão caso nula
			if(connection == null) {
				openConnection();
			}		
			
			Cliente cliente = new Cliente();
			
			StringBuilder sql = new StringBuilder();
					
			sql.append("SELECT *");
			sql.append(" FROM " + table + " pes");
			sql.append(" INNER JOIN tbl_usuario usr ON usr.usr_pes_in_id = pes.pes_in_id");
			sql.append(" WHERE usr.usr_bo_status");
			sql.append(" AND pes.pes_bo_status");
			sql.append(" AND usr_in_tipo = " + TipoUsuario.CLIENTE.ordinal());
			sql.append(" ORDER BY pes.pes_st_nome");
			
			try {
				pst = connection.prepareStatement(sql.toString());
				
				rs = pst.executeQuery();
				
				List<Cliente> AllClientes = new ArrayList<Cliente>();
				
				while(rs.next()) {
					
					cliente = new Cliente();
					
					//Convertendo data recebida do Banco
					Date date = (rs.getDate("pes_dt_dt_nasc"));
					Instant instant = Instant.ofEpochMilli(date.getTime());
					LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
					
					//Dados de Pessoa
					cliente.setId(rs.getInt("pes_in_id"));
					cliente.setNome(rs.getString("pes_st_nome"));
					cliente.setSobrenome(rs.getString("pes_st_sobrenome"));
					cliente.setRg(rs.getString("pes_in_rg"));
					cliente.setCpf(rs.getString("pes_in_CPF"));
					cliente.setGenero(TipoGenero.valueOf(rs.getString("pes_ch_genero")));
					cliente.setDtNasc(localDate);
					//Dados de Usuário
					cliente.setUsr_id(rs.getInt("usr_in_id"));
					cliente.setEmail(rs.getString("usr_st_email"));
					cliente.setSenhaBD(rs.getString("usr_st_senha"));
					cliente.setTipo(TipoUsuario.CLIENTE);
					cliente.setUsr_status(rs.getBoolean("usr_bo_status"));
				
					AllClientes.add(cliente);
				}
				return AllClientes;
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
