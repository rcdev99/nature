package br.com.fatec.les.nature.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.model.SiglaEstados;
import br.com.fatec.les.nature.model.TipoProduto;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoTelefone;
import br.com.fatec.les.nature.model.TipoUsuario;
import br.com.fatec.les.nature.service.ProdutoService;

@Controller
public class HomeController {
	
	//DAO´S
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	//Service
	@Autowired
	ProdutoService pService;
	
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
	 * Método responsável por invocar a página principal de exibição dos produtos do sistema. 
	 * @return
	 */
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ModelAndView mostrarProdutos(){
		
		ModelAndView mView = new ModelAndView("produtos");
		
		List<Produto> produtos = pService.getAllProducts();
		
		mView.addObject("produtos", produtos);
		mView.addObject("tiposProduto", TipoProduto.values());
		
		return mView;
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
	public ModelAndView cadastrarUsuario(Cliente cliente) {
		ModelAndView mView = new ModelAndView("cadastro");
		
		mView.addObject("tiposResidencia", TipoResidencia.values());
		mView.addObject("tiposTelefone", TipoTelefone.values());
		mView.addObject("siglasEstado", SiglaEstados.values());
		mView.addObject("cliente", cliente);
		
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
		qtdClientes = DAOUsuario.getQtdUsuarios(TipoUsuario.ROLE_CLIENTE);
	
		mView.addObject("qtdClientes", qtdClientes);
		
		return mView;
	}
	
}
