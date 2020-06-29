package br.com.fatec.les.nature.controller;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.dto.ComprasMensalDTO;
import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.model.SiglaEstados;
import br.com.fatec.les.nature.model.TipoProduto;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoTelefone;
import br.com.fatec.les.nature.model.TipoUsuario;
import br.com.fatec.les.nature.service.CompraService;
import br.com.fatec.les.nature.service.ProdutoService;

@Controller
public class HomeController {
	
	//DAO´S
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	//Service
	@Autowired
	ProdutoService pService;
	
	@Autowired
	CompraService compraService;
	
	//Carrinho
	@Autowired
	Carrinho carrinho;
	
	/**
	 * Método utilizado para direcionar o cliente à tela de login
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView autenticacao() {
		
		ModelAndView mView = new ModelAndView("login");
		
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
	}
	
	@RequestMapping(value = "/layout/nature")
	public ModelAndView layoutNature() {
		
		ModelAndView mView = new ModelAndView("layout/LayoutPadraoNature");
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		return mView;
	}
	
	@RequestMapping(value = "/layout/dashboard")
	public ModelAndView layoutNatureDashboard() {
		
		ModelAndView mView = new ModelAndView("layout/LayoutPadraoDashboard");
		return mView;
	}
	
	
	@RequestMapping(value = "")
	public String redirect() {
		
		return "redirect:/home";
	}
	
	/**
	 * Método reponsável por invocar a home page do sistema.
	 * @return String contendo o nome do arquivo html a ser invocado
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView getIndex() {
		
		ModelAndView mView = new ModelAndView("home");
		
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
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
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de conclusão da venda
	 * @return
	 */
	@RequestMapping(value = "/conclusao")
	public ModelAndView conclusao() {
		
		ModelAndView mView = new ModelAndView("conclusao");
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de conclusão da venda
	 * @return
	 */
	@RequestMapping(value = "/lista_de_desejos")
	public ModelAndView exibirListaDeDesejos() {
		
		ModelAndView mView = new ModelAndView("carrinho");
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view com informações sobre o grupo NaturÊ
	 * @return
	 */
	@RequestMapping(value = "/sobre_nos")
	public ModelAndView sobreNos() {
		
		ModelAndView mView = new ModelAndView("sobre_nos");
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		return mView;
		
	}
	
	/**
	 * Método utilizado para direcionar o cliente à view de contatos com o grupo NaturÊ
	 * @return
	 */
	@RequestMapping(value = "/contato")
	public ModelAndView contato() {
		
		ModelAndView mView = new ModelAndView("contato");
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		
		return mView;
		
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
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		mView.addObject("cliente", cliente);
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o usuário administrativo ao painel de controle
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/admin")
	public ModelAndView painelDeControleAdmin(Integer periodo) throws SQLException {
		ModelAndView mView = new ModelAndView("dashboard-admin");
	
		Integer qtdClientes;
		qtdClientes = DAOUsuario.getQtdUsuarios(TipoUsuario.ROLE_CLIENTE);
		
		//Valor default para geração do gráfico
		if(periodo == null) {
			periodo = 6;
		}
		
		//Map de associação entre o mês e a quantidade de vendas
		Map<String, Integer> qtdComprasMensal = new LinkedHashMap<String, Integer>();
		Map<String, Integer> qtdEntreguesMensal = new LinkedHashMap<String, Integer>();
		Map<String, Integer> qtdCanceladasMensal = new LinkedHashMap<String, Integer>();
		
		//Populando Map´s
		for (ComprasMensalDTO comprasMensal: compraService.obterQuantidadeComprasUltimosMeses(periodo)) {
			qtdComprasMensal.put(comprasMensal.getMesTxt(), comprasMensal.getQtdCompras());
		}
		for (ComprasMensalDTO entreguesMensal: compraService.obterQuantidadeEntreguesUltimosMeses(periodo)) {
			qtdEntreguesMensal.put(entreguesMensal.getMesTxt(), entreguesMensal.getQtdCompras());
		}
		for (ComprasMensalDTO canceladasMensal: compraService.obterQuantidadeCanceladasUltimosMeses(periodo)) {
			qtdCanceladasMensal.put(canceladasMensal.getMesTxt(), canceladasMensal.getQtdCompras());
		}
		
		mView.addObject("qtdClientes", qtdClientes);
		mView.addObject("comprasMensal", qtdComprasMensal);
		mView.addObject("entreguesMensal", qtdEntreguesMensal);
		mView.addObject("canceladasMensal", qtdCanceladasMensal);
		mView.addObject("selecionado", periodo);
		
		return mView;
	}
	
	/**
	 * Método para obtenção do login do usuário
	 * @return String contendo login do usuário
	 */
	@SuppressWarnings("unused")
	private String obterUsuarioLogado() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;    

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		return userName;
	}
	
}
