package br.com.fatec.les.nature.fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.IDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.negocio.IStrategy;
import br.com.fatec.les.nature.negocio.ValidadorDadosObrigatoriosCliente;


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
		
		//Mapeando DAO de acordo com o nome da classe
		daos.put(Cliente.class.getName(), DAOCliente);
		
		//Instanciando as regras de Negócio
		ValidadorDadosObrigatoriosCliente valDadosObrigatorios = new ValidadorDadosObrigatoriosCliente();
		
		//Lista de regras de negócio para cada operação
		List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
		
		//Adicionando regras de negócio à operação
		rnsSalvarCliente.add(valDadosObrigatorios);
		
		//Mapa contendo a lista de regras de negócio por operação
		Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
		
		//Adicionando lista de regras de negócio ao mapa do cliente
		rnsCliente.put("SALVAR", rnsSalvarCliente);
		
		rns.put(Cliente.class.getName(), rnsCliente);
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
		// TODO Auto-generated method stub
		return null;
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
							msg.append("\n");
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
