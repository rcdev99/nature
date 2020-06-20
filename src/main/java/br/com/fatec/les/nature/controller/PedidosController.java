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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.EnderecoDAO;
import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Compra;
import br.com.fatec.les.nature.model.CupomDesconto;
import br.com.fatec.les.nature.model.Usuario;
import br.com.fatec.les.nature.service.CompraService;
import br.com.fatec.les.nature.service.CupomService;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {

	@Autowired
	Carrinho carrinho;
	
	@Autowired
	CompraService compraService;
	
	@Autowired
	CupomService cupomService;
	
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
		
		ModelAndView mView = new ModelAndView("pedido_detalhes");
	
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
		
		mView.addObject("qtdProduto", carrinho.getQtdProdutos());
		mView.addObject("compraValida", compraValida);
		mView.addObject("compra", compra);
		mView.addObject("endereco", DAOEndereco.consultaById(compra.getIdEndereco()));
		mView.addObject("itensCompra", compra.getItens());
	
		return mView;
		
	}
	
	@RequestMapping(value="/cancelar/{id.compra}")
	public ModelAndView cancelarPedido(@PathVariable("id.compra") Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mView = new ModelAndView("redirect:/pedidos/meus");
		
		//Obter cliente logado
		Cliente cliente = new Cliente();
		cliente = DAOCliente.consultaById(obterIdCliente());
	
		Compra compra = new Compra();
		compra = compraService.buscarCompraPorId(id);
		
		String msgCancelamento;
		
		if(compra != null && (compra.getIdCliente().equals(cliente.getId()))) {	
			
			if(compra.cancelar()) {
				
				CupomDesconto cupom = new CupomDesconto(compra.getIdCliente(), compra.getTotal(), ("Cupom gerado em decorrência de cancelamento do pedido #" + compra.getId()));
				
				compraService.salvar(compra);
				cupomService.salvar(cupom);
				
				msgCancelamento = "Pedido #" + compra.getId() + " cancelado, o valor será estornado em forma de cupom no valor da compra";
			}else {
				msgCancelamento = ("Compras com status: '" + compra.getSituacao().getDescricao() + "' não podem ser canceladas.");
			}
			
		}else {
			msgCancelamento = "Você não pode cancelar esta compra";
		}
		
		redirectAttributes.addFlashAttribute("msgCancelamento", msgCancelamento);
		
		return mView;
	}
	
	@RequestMapping(value="/adm/acompanhar" )
	public ModelAndView acompranharPedidos() {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-pedidos");
		
		mView.addObject("pedidos", compraService.buscarTodasAsCompras());
		
		
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
