package br.com.fatec.les.nature.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.Carteira;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.TipoBandeira;
import br.com.fatec.les.nature.model.Usuario;
import br.com.fatec.les.nature.service.CompraService;
import br.com.fatec.les.nature.service.ProdutoService;

@Controller
@RequestMapping("/compra")
public class CompraController {

	//DAO´S
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	ClienteDAO DAOCliente = new ClienteDAO();
	
	@Autowired
	CompraService comService;
	
	@Autowired
	Carrinho carrinho;
	
	@Autowired
	Carteira carteira;
	
	@Autowired
	ProdutoService prodService;
	
	

	/**
	 * Método utilizado para direcionar o cliente à view de conclusão da compra
	 * @return
	 */
	@RequestMapping(value = "/conclusao")
	public ModelAndView conclusao() {
		
		ModelAndView mView = new ModelAndView("conclusao");
		
		//Obter cliente da compra
		Cliente cliente = new Cliente();
		cliente = DAOCliente.consultaById(obterIdCliente());
		
		BigDecimal desconto = carteira.totalDescontos();
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		mView.addObject("cliente", cliente);
		mView.addObject("enderecos", cliente.getEnderecos());
		mView.addObject("totalProdutos", carrinho.valorProdutos());
		mView.addObject("frete", carrinho.getFrete());
		mView.addObject("desconto", desconto);
		mView.addObject("totalCompra", carrinho.totalCompra(desconto));
		mView.addObject("tiposBandeira", TipoBandeira.values());
		
		return mView;
	}
	
	/**
	 * Método para obtenção do login do usuário
	 * @return String contendo login do usuário
	 */
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
	
	private int obterIdCliente() {

		Usuario user = new Cliente();
		user = DAOUsuario.consultaByLogin(obterUsuarioLogado());
		
		return user.getId();
	}
		
}
