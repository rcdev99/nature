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
import br.com.fatec.les.nature.command.AlterarCommand;
import br.com.fatec.les.nature.command.ICommand;
import br.com.fatec.les.nature.command.SalvarCommand;
import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.SiglaEstados;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoTelefone;
import br.com.fatec.les.nature.view.ClienteViewHelper;
import br.com.fatec.les.nature.view.GetHelper;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	ICommand command;
	ClienteDAO DAOCliente = new ClienteDAO();
	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	/**
	 * Método utilizado para direcionar o cliente à tela de login
	 * @return
	 */
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		command = new SalvarCommand();
		Cliente cliente = new Cliente();
		ClienteViewHelper helperCliente = new ClienteViewHelper();
		cliente = (Cliente) helperCliente.getEntidade(request);
		
		Resultado resultado = command.execute(helperCliente.getEntidade(request));  
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
			ModelAndView mView = new ModelAndView("redirect:/cadastro");
			
			String msg = resultado.getMsg();
			redirectAttributes.addFlashAttribute("resultado", msg);
			redirectAttributes.addFlashAttribute("cliente", cliente);
			
			return mView;
		}
	}
	/**
	 * Método utilizado para direcionar o usuário administrativo ao painel de controle de clientes
	 * @return
	 */
	@RequestMapping(value = "/listar")
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
	
	/**
	 * Método para realizar a exclusão lógica de um determinado usuário
	 * @param id - Identificador único para o usuário
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/excluir/{cliente.id}")
	public ModelAndView desativarCliente(@PathVariable("cliente.id") Integer id) throws SQLException {
		ModelAndView mView = new ModelAndView("redirect:/cliente/listar");
		Cliente cliente = new Cliente();
		
		cliente = DAOUsuario.consultaById(id);
		DAOUsuario.excluir(cliente);
		
		return mView;
	}
	/**
	 * Método para exibição dos detalhes cadastrais de um determinado Cliente
	 * @param id Identificar único do Usuário
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/detalhes/{cliente.id}", method=RequestMethod.GET)
	public ModelAndView exibirCliente(@PathVariable("cliente.id") Integer id, RedirectAttributes redirectAttributes) throws SQLException {
		
		Cliente cliente = new Cliente();
		List<Endereco> enderecos = new ArrayList<Endereco>();
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		ModelAndView mView = new ModelAndView("dashboard-adm-cliente-detalhes"); 
		cliente = DAOCliente.consultaById(id);
		
		enderecos = cliente.getEnderecos();
		telefones = cliente.getTelefones();
		
		mView.addObject("cliente", cliente);
		mView.addObject("enderecos", enderecos);
		mView.addObject("telefones", telefones);

		return mView;
	}
	
	/**
	 * Método responsável por direcionar o usuário à tela de edição de dados de um determinado Cliente
	 * @param id Identificador único do cliente cujo os dados poderão ser alterados
	 * @param redirectAttributes
	 * @return Tela de visualização de dados do Cliente
	 * @throws SQLException
	 */
	@RequestMapping(value = "/editar/{cliente.id}", method=RequestMethod.GET)
	public ModelAndView editarCliente(@PathVariable("cliente.id") Integer id, RedirectAttributes redirectAttributes) throws SQLException {
		ModelAndView mView = new ModelAndView("dashboard-adm-cliente-editar"); 
		Cliente cliente = new Cliente();
		
		cliente = DAOCliente.consultaById(id);
		
		mView.addObject("cliente", cliente);
		mView.addObject("enderecos", cliente.getEnderecos());
		mView.addObject("telefones", cliente.getTelefones());
		mView.addObject("tiposTelefone", TipoTelefone.values());
		mView.addObject("tiposResidencia", TipoResidencia.values());
		mView.addObject("siglasEstado", SiglaEstados.values());
		
		return mView;
	}
	
	/***
	 * Método responsável por conduzir a alteração de dados de um determinado cliente
	 * @return
	 */
	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public ModelAndView alterarDadosCliente(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		command = new AlterarCommand();
		GetHelper getHelper = new GetHelper();
		List<String>mensagens = new ArrayList<String>();
		boolean sucesso;
		
		//Definição da objeto a alterar
		String entidade = request.getParameter("entidade"); 
		
		//Redirecionamento da página
		Integer id = Integer.valueOf(request.getParameter("id_cliente"));
		ModelAndView mView = new ModelAndView("redirect:/cliente/editar/" + id);
		
		//Executando as regras de negócio para a entidade retornada pelo GetHelper();
		Resultado resultado = command.execute(getHelper.getEntidade(request));
		resultado.setMsg(resultado.getMsg().replace("null", ""));
		//Tratamento da mensagem de retorno
		if(resultado.getMsg().isBlank() || resultado.getMsg().isEmpty()) {
			
			mensagens.add(entidade + " ALTERADO COM SUCESSO !"); 
			sucesso = true;
			
		}else {
			String msg = resultado.getMsg();
			sucesso = false;
			
			String[] msgSeparada = msg.split("\n"); 
			
			for (String string : msgSeparada) {
				mensagens.add(string);
			}
			
			mensagens.remove(0); 
			
		}
		
		redirectAttributes.addFlashAttribute("resultado", mensagens);
		redirectAttributes.addFlashAttribute("sucesso", sucesso);
		
		return mView;
	}
	
	
}
