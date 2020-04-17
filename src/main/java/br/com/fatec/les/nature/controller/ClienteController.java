package br.com.fatec.les.nature.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.command.ICommand;
import br.com.fatec.les.nature.command.SalvarCommand;
import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.view.ClienteViewHelper;

@Controller
public class ClienteController {

	ICommand command;
	ClienteDAO DAOCliente = new ClienteDAO();
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	/**
	 * Método utilizado para direcionar o cliente à tela de login
	 * @return
	 */
	@RequestMapping(value = "/cliente/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		command = new SalvarCommand();
		Cliente cliente = new Cliente();
		ClienteViewHelper helperCliente = new ClienteViewHelper();
		cliente = (Cliente) helperCliente.getEntidade(request);
		
		Resultado resultado = command.execute(cliente);  
		resultado.setMsg(resultado.getMsg().replace("null", ""));
		
		if(resultado.getMsg().isBlank() || resultado.getMsg().isEmpty()) {
			
			try {
				DAOCliente.salvar(cliente);
			} catch (SQLException e) {
				System.out.println("Erro durante processo de persistência do objeto");
				e.printStackTrace();
			}
			
			redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado com sucesso!");
			return new ModelAndView("redirect:/login");
			
		}else {
			
			
			String msg = resultado.getMsg();
			redirectAttributes.addFlashAttribute("resultado", msg);
			redirectAttributes.addFlashAttribute("cliente", cliente);
			
			return new ModelAndView("redirect:/cadastro");
		}
	}
	/**
	 * Método utilizado para direcionar o usuário administrativo ao painel de controle de clientes
	 * @return
	 */
	@RequestMapping(value = "/clientes")
	public ModelAndView getClientes() {
		
		//Variavéis locais
		ModelAndView mView = new ModelAndView("dashboard-adm-clientes");
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		//Obtendo lista de clientes
		clientes = DAOCliente.getClientes();
		
		//Incluindo objetos na view
		mView.addObject("clientes", clientes);
		
		return mView;
	}
	
	@RequestMapping(value = "/cliente/excluir/{cliente.id}")
	public ModelAndView desativarCliente(@PathVariable("cliente.id") Integer id) throws SQLException {
		ModelAndView mView = new ModelAndView("redirect:/visualizar");
		Cliente cliente = new Cliente();
		
		cliente = DAOUsuario.consultaById(id);
		DAOUsuario.excluir(cliente);
		
		return mView;
	}
	
}
