package br.com.fatec.les.nature.fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.EnderecoDAO;
import br.com.fatec.les.nature.dao.IDAO;
import br.com.fatec.les.nature.dao.TelefoneDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.negocio.IStrategy;
import br.com.fatec.les.nature.negocio.ValidadorAlterarCliente;
import br.com.fatec.les.nature.negocio.ValidadorDadosObrigatoriosCliente;
import br.com.fatec.les.nature.negocio.ValidadorEndereco;
import br.com.fatec.les.nature.negocio.ValidadorTelefone;


public class Fachada implements IFachada{

	private Map<String, Map<String, List<IStrategy>>> rns;
	private Map<String, IDAO> daos;
	private Resultado resultado;
	
	/**
	 * Método construtor da classe
	 */
	public Fachada() {
		
		// Mapa de DAOS
		daos = new HashMap<String, IDAO>();
		
		// Mapa de Regras de Neg�cio
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
		
		//Instancia de DAOS a serem utilizados
		ClienteDAO DAOCliente = new ClienteDAO();
		EnderecoDAO DAOEndereco = new EnderecoDAO();
		TelefoneDAO DAOTelefone = new TelefoneDAO();
		
		//Mapeando DAO de acordo com o nome da classe
		daos.put(Cliente.class.getName(), DAOCliente);
		daos.put(Endereco.class.getName(), DAOEndereco);
		daos.put(Telefone.class.getName(), DAOTelefone);
		
		//Instanciando as regras de Negócio
		ValidadorDadosObrigatoriosCliente valDadosObrigatorios = new ValidadorDadosObrigatoriosCliente();
		ValidadorAlterarCliente valAlterarCliente = new ValidadorAlterarCliente();
		ValidadorEndereco valEndereco = new ValidadorEndereco();
		ValidadorTelefone valTelefone = new ValidadorTelefone();
		
		//Lista de regras de negócio para cada operação
		List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarTelefone = new ArrayList<IStrategy>();
		
		//Adicionando regras de negócio à operação
		rnsSalvarCliente.add(valDadosObrigatorios);
		rnsAlterarCliente.add(valAlterarCliente);
		rnsAlterarEndereco.add(valEndereco);
		rnsAlterarTelefone.add(valTelefone);
		
		//Mapa contendo a lista de regras de negócio por operação
		Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
		Map<String, List<IStrategy>> rnsEndereco = new HashMap<String, List<IStrategy>>();
		Map<String, List<IStrategy>> rnsTelefone = new HashMap<String, List<IStrategy>>();
		
		//Adicionando lista de regras de negócio ao mapa da Entidade
		rnsCliente.put("SALVAR", rnsSalvarCliente);
		rnsCliente.put("ALTERAR", rnsAlterarCliente);
		rnsEndereco.put("ALTERAR", rnsAlterarEndereco);
		rnsTelefone.put("ALTERAR", rnsAlterarTelefone);
		
		rns.put(Cliente.class.getName(), rnsCliente);
		rns.put(Endereco.class.getName(), rnsEndereco);
		rns.put(Telefone.class.getName(), rnsTelefone);
	
	}
	
	@Override
	public Resultado salvar(EntidadeDominio entidadeDominio) {

		resultado = new Resultado();
		String nmClasse = entidadeDominio.getClass().getName();	
		
		String msg = executarRegras(entidadeDominio, "SALVAR");
		
		
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				dao.salvar(entidadeDominio);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidadeDominio);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");
			}
		}else{
			resultado.setMsg(msg);
		}
		
		return resultado;
		
		
	}

	@Override
	public Resultado alterar(EntidadeDominio entidadeDominio) {
		
		resultado = new Resultado();
		String nmClasse = entidadeDominio.getClass().getName();	
		
		String msg = executarRegras(entidadeDominio, "ALTERAR");
		
		//Tratamento de resposta para casos de validações múltiplas
		if(msg != null) {
			msg = msg.replace("null", "");
		}else if( msg == null) {
			
			msg = "";
		}
		
		if(msg.isBlank() || msg.isEmpty()){
			IDAO dao = daos.get(nmClasse);
			try {
				dao.alterar(entidadeDominio);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidadeDominio);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível alterar o registro!");
			}
			resultado.setMsg("");
		}else{
			resultado.setMsg(msg);
		}
		
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidadeDominio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidadeDominio) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String executarRegras(EntidadeDominio entidade, String operacao) {
			
			String nmClasse = entidade.getClass().getName();		
			StringBuilder msg = new StringBuilder();
			
			Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);
			
			if(regrasOperacao != null){
				List<IStrategy> regras = regrasOperacao.get(operacao);
				
				if(regras != null){
					for(IStrategy s: regras){			
						String m = s.processar(entidade);			
						
						if(m != null){
							msg.append(m);
						}			
					}	
				}			
			}
			
			if(msg.length()>0)
				return msg.toString();
			else
				return null;
		}

}
