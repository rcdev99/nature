package br.com.fatec.les.nature.negocio;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;

public class ValidadorDadosObrigatoriosCliente implements IStrategy{

	//Instanciando variáveis locais
	boolean incorreto = false;
	StringBuilder sb = new StringBuilder();
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	
	//Validadores necessários
	ValidadorNome validaNome = new ValidadorNome();
	ValidadorCPF validaCPF = new ValidadorCPF();
	ValidadorRG validaRG = new ValidadorRG();
	ValidadorEndereco validaEndereco = new ValidadorEndereco();
	ValidadorTelefone validaTelefone = new ValidadorTelefone();
	
	@Override
	public String processar(EntidadeDominio entidadeDominio) {
		
		if(entidadeDominio instanceof Cliente) {
			//Casting para cliente
			Cliente cliente = (Cliente) entidadeDominio;
			
			//Instanciando variaveis necessarias
			List<Telefone> telefones = new ArrayList<Telefone>(); 
			List<Endereco> enderecos = new ArrayList<Endereco>(); 
			
			//Validando dados pessoais
			sb.append(validaNome.processar(cliente));
			sb.append(validaCPF.processar(cliente));
			sb.append(validaRG.processar(cliente));
			
			if(cliente.getGenero().equals(null)) {
				
				incorreto = true;
				sb.append("\nPreencha corretamento o campo 'Genero'.");
			}
			
			if(cliente.getDtNasc().equals(null)) {
				
				incorreto = true;
				sb.append("\nÉ necessária a inserção da data de nascimento.");
			}
			
			if(cliente.getEmail() == null) {
				
				incorreto = true;
				sb.append("\nÉ obrigatório o preenchimento do e-mail");
			}else {
				
				Cliente cli = new Cliente();
				cli = DAOUsuario.consultaByLogin(cliente.getEmail());
				
				if(cli != null) {
					
					incorreto = true;
					sb.append("\nJá há um cadastro realizado com o e-mail informado");
				}
				
			}
			
			//Validação da lista de endereços
			if(cliente.getEnderecos().isEmpty() || cliente.getEnderecos() == null) {
				
				incorreto = true;
				sb.append("\nO cliente deve ter ao menos um endereço cadastrado.");
				
			}else {
				
				enderecos = cliente.getEnderecos();
				
				for (Endereco endereco : enderecos) {
					
					Endereco end = (Endereco) endereco;
					end = endereco;
					
					sb.append(validaEndereco.processar(end));
				}
				
				
			}
			//Validação da lista de telefones
			if(cliente.getTelefones().isEmpty() || cliente.getTelefones().equals(null)) {
				
				incorreto = true;
				sb.append("\nO cliente deve ter ao menos um telefone cadastrado.");
				
			}else {
				
				telefones = cliente.getTelefones();
				
				for (Telefone telefone: telefones) {
					
					sb.append(validaTelefone.processar(telefone));
				}
				
			}
			
			//Retorno caso algum campo tenha sido preenchido incorretamente
			if(incorreto || sb.toString() != null) {
				return sb.toString();
			}
			
		}else {
			return "Entidade não pôde ser instanciada, pois não é um cliente.";
		}
		
		return null;
	}

}
