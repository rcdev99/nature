package br.com.fatec.les.nature.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.EnderecoDAO;
import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Compra;
import br.com.fatec.les.nature.model.Usuario;
import br.com.fatec.les.nature.service.CompraService;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {

	@Autowired
	Carrinho carrinho;
	
	@Autowired
	CompraService compraService;
	
	//DAO´S
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	ClienteDAO DAOCliente = new ClienteDAO();
	EnderecoDAO DAOEndereco = new EnderecoDAO();
	
	@RequestMapping(value="/meus")
	public ModelAndView meusPedidos() {
		 
		ModelAndView mView = new ModelAndView("meus_pedidos");
		
		//Obter cliente da compra
		Cliente cliente = new Cliente();
		cliente = DAOCliente.consultaById(obterIdCliente());
		
		List<Compra>compras = new ArrayList<Compra>();
		boolean temCompras = false;
		
		compras = compraService.buscarCompraPorCliente(cliente.getId());
		
		if(compras.size() > 0) {
			temCompras = true;
		}

		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		mView.addObject("compras", compras);
		mView.addObject("temCompras", temCompras);
		
		return mView;
	}
	
	@RequestMapping(value="/detalhes/{id.compra}")
	public ModelAndView detalhesPedido(@PathVariable("id.compra") Long id) {
		
		ModelAndView mView = new ModelAndView("pedidos_detalhes");
	
		//Obter cliente logado
		Cliente cliente = new Cliente();
		cliente = DAOCliente.consultaById(obterIdCliente());
		
		Boolean compraValida;
		Compra compra = new Compra();
		
		compra = compraService.buscarCompraPorId(id);
		
		if(compra != null && (compra.getIdCliente().equals(cliente.getId()))) {
			compraValida = true;
		}else {
			compraValida = false;
		}
		
		System.out.println("id na compra: " + compra.getIdCliente());
		System.out.println("id do cliente: " + cliente.getId());
		System.out.println();
		
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		mView.addObject("compraValida", compraValida);
		mView.addObject("compra", compra);
		mView.addObject("endereco", DAOEndereco.consultaById(compra.getIdEndereco()));
		mView.addObject("itensCompra", compra.getItens());
	
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
