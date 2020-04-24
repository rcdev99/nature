package br.com.fatec.les.nature.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;

public class GetHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		String entidade = request.getParameter("entidade"); 
		
		switch (entidade) {
		case "CLIENTE": {
		
			Cliente cliente = new Cliente();
			ClienteViewHelper vhCli = new ClienteViewHelper();
			cliente = (Cliente) vhCli.getEntidade(request);
			
			return cliente;
		}
		case "ENDERECO": {
			
			Endereco endereco = new Endereco();
			EnderecoViewHelper vhEnd = new EnderecoViewHelper();
			endereco = (Endereco) vhEnd.getEntidade(request);
			
			return endereco;
		}
		case "TELEFONE": {
					
			Telefone telefone = new Telefone();
			TelefoneViewHelper vhTel = new TelefoneViewHelper();
			telefone = (Telefone) vhTel.getEntidade(request);
			
			return telefone;
		}
		default:
			System.out.println("Entidade " + entidade + "Não possui um Helper mapeado");
			return null;
		}
		
		
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
