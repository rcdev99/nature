package br.com.fatec.les.nature.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.SiglaEstados;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoTelefone;
import br.com.fatec.les.nature.model.TipoUsuario;

@Controller
public class HomeController {
	
	//DAO´S
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	/**
	 * Método utilizado para direcionar o cliente à tela de login
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String autenticacao() {
		return "login";
		
	}
	
	/**
	 * Método reponsável por invocar a home page do sistema.
	 * @return String contendo o nome do arquivo html a ser invocado
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getIndex() {
		System.out.println("Entry point NaturÊ");
		return "index";
	}
	
	/**
	 * Método responsável por incovar a página de exibição dos produtos do sistema. 
	 * @return
	 */
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public String mostrarProdutos(){
		
		return "produtos";
	}
	
	/**
	 * Método responsável por incovar a página de exibição dos detalhes de um produto. 
	 * @return
	 */
	@RequestMapping(value = "/produto")
	public String detalhesProduto(){
		
		return "produto";
	}
	
	/**
	 * Método utilizado para direcionar o cliente ao carrinho de compras
	 * @return
	 */
	@RequestMapping(value = "/carrinho")
	public String carrinho() {
		return "carrinho";
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de conclusão da venda
	 * @return
	 */
	@RequestMapping(value = "/conclusao")
	public String conclusao() {
		return "conclusao";
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de conclusão da venda
	 * @return
	 */
	@RequestMapping(value = "/lista_de_desejos")
	public String exibirListaDeDesejos() {
		return "lista_desejos";
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view com informações sobre o grupo NaturÊ
	 * @return
	 */
	@RequestMapping(value = "/sobre_nos")
	public String sobreNos() {
		return "sobre_nos";
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de contatos com o grupo NaturÊ
	 * @return
	 */
	@RequestMapping(value = "/contato")
	public String contato() {
		return "contato";
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de cadastro de usuários
	 * @return
	 */
	@RequestMapping(value = "/cadastro")
	public ModelAndView cadastrarUsuario() {
		ModelAndView mView = new ModelAndView("cadastro");
		
		mView.addObject("tiposResidencia", TipoResidencia.values());
		mView.addObject("tiposTelefone", TipoTelefone.values());
		mView.addObject("siglasEstado", SiglaEstados.values());
		
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o usuário administrativo ao painel de controle
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/admin")
	public ModelAndView painelDeControleAdmin() throws SQLException {
		ModelAndView mView = new ModelAndView("dashboard-admin");
	
		Integer qtdClientes;
		qtdClientes = DAOUsuario.getQtdUsuarios(TipoUsuario.CLIENTE);
	
		mView.addObject("qtdClientes", qtdClientes);
		
		return mView;
	}
	
}
