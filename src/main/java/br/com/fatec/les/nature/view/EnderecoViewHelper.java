package br.com.fatec.les.nature.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.Cidade;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Estado;
import br.com.fatec.les.nature.model.Logradouro;
import br.com.fatec.les.nature.model.TipoLogradouro;
import br.com.fatec.les.nature.model.TipoResidencia;

public class EnderecoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		//Instanciando variáveis locais
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		
		//Identificando a operação
		String operacao = request.getParameter("operacao");
		
		//Executando construção da entidade com os dados da requisição
		if(operacao != null) {
			if(operacao.contentEquals("ALTERAR")) {
				
				//Endereco 
				Integer numero = Integer.valueOf(request.getParameter("numero"));
				Integer id_pessoa = Integer.valueOf(request.getParameter("id_cliente"));
				Integer id = Integer.valueOf(request.getParameter("id"));
				String tipoResidencia = request.getParameter("tipoResidencia");
				
				//Compondo endereço do cliente
				cidade.setCidade(request.getParameter("cidade"));
				endereco.setBairro(request.getParameter("bairro"));
				endereco.setCep(request.getParameter("cep"));
				endereco.setDescricao(request.getParameter("descricao"));
				estado.setSigla(request.getParameter("sigla"));
				logradouro.setLogradouro(request.getParameter("logradouro"));
				logradouro.setTipo(TipoLogradouro.RUA);
				cidade.setEstado(estado);
				endereco.setLogradouro(logradouro);
				endereco.setNumero(numero);
				endereco.setTipoResidencia(TipoResidencia.valueOf(tipoResidencia));
				endereco.setCidade(cidade);
				endereco.setIdPessoa(id_pessoa);
				endereco.setId_endereco(id);
				
				return endereco;
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
