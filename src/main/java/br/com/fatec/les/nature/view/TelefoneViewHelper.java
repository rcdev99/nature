package br.com.fatec.les.nature.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoTelefone;

public class TelefoneViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		//Instanciando variáveis locais
		Telefone telefone = new Telefone();
		
		//Identificando a operação
		String operacao = request.getParameter("operacao");
		
		//Executando construção da entidade com os dados da requisição
		if(operacao != null) {
			if(operacao.contentEquals("ALTERAR")) {
				
				Integer id = Integer.valueOf(request.getParameter("id"));
				String tipo = request.getParameter("tipo");
				
				//Construção do objeto
				telefone.setIdTelefone(id);
				telefone.setDdd(request.getParameter("DDDtelefone"));
				telefone.setNumero(request.getParameter("numero"));
				telefone.setTipo(TipoTelefone.valueOf(tipo));				
				
				return telefone;
			}
		}
		
		return null;
		
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
