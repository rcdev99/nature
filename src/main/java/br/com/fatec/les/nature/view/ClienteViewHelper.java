package br.com.fatec.les.nature.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.les.nature.Resultado;
import br.com.fatec.les.nature.model.Cidade;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Estado;
import br.com.fatec.les.nature.model.Logradouro;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoGenero;
import br.com.fatec.les.nature.model.TipoLogradouro;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoTelefone;
import br.com.fatec.les.nature.model.TipoUsuario;
import br.com.fatec.les.nature.util.FormataData;

public class ClienteViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		Cliente cliente = new Cliente();
		FormataData fd = new FormataData();
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		//Identificando a operação
		String operacao = request.getParameter("operacao");
		
		if(operacao != null) {
			if(operacao.contentEquals("SALVAR")) {
				/*Obtendo dados da requisição*/
				//Dados Pessoais
				String nome = request.getParameter("nome");
				String sobrenome = request.getParameter("sobrenome");
				String dtNasc = request.getParameter("dtNasc");
				String cpf = request.getParameter("cpf");
				String rg = request.getParameter("rg");
				String genero = request.getParameter("genero");
				//Credenciais de acesso
				String email = request.getParameter("email");
				String senha = request.getParameter("senha");
				//Endereco
				String nomeRua = request.getParameter("logradouro");
				Integer numero = Integer.valueOf(request.getParameter("numero")); 
				String tipoResidencia = request.getParameter("tipoResidencia");
				String bairro = request.getParameter("bairro");
				String cep = request.getParameter("cep");
				String city = request.getParameter("cidade");
				String sigla = request.getParameter("sigla");
				String descricao = request.getParameter("descricao");
				//Telefone
				String telefone1 = request.getParameter("numero1");				
				String telefone2 = request.getParameter("numero2");
				
				//Instancia de data de nascimento
				LocalDate data = LocalDate.parse(dtNasc, fd.getFormatoView());
				
				//Compondo endereço do cliente
				logradouro.setLogradouro(nomeRua);
				logradouro.setTipo(TipoLogradouro.RUA);
				estado.setSigla(sigla);
				cidade.setCidade(city);
				cidade.setEstado(estado);
				endereco.setLogradouro(logradouro);
				endereco.setNumero(numero);
				endereco.setTipoResidencia(TipoResidencia.valueOf(tipoResidencia));
				endereco.setBairro(bairro);
				endereco.setCep(cep);
				endereco.setCidade(cidade);
				endereco.setDescricao(descricao);
				
				//Compondo telefones do cliente
				if(telefone1 != null) {
					telefone1 = telefone1.trim();
					telefone1 = telefone1.replaceAll("[^0-9]", "");
					
					if(!telefone1.isBlank() || !telefone1.isEmpty()) {
						Telefone tel1 = new Telefone();
						tel1.setDdd(request.getParameter("DDDtelefone1"));
						tel1.setNumero(telefone1);
						tel1.setTipo(TipoTelefone.valueOf(request.getParameter("tipoTelefone1")));
						telefones.add(tel1);
					}
				}
				if(telefone2 != null) {
					telefone2 = telefone1.trim();
					telefone2 = telefone1.replaceAll("[^0-9]", "");
					
					if(!telefone2.isBlank() || !telefone2.isEmpty()) {
						Telefone tel2 = new Telefone();
						tel2.setDdd(request.getParameter("DDDtelefone2"));
						tel2.setNumero(telefone2);
						tel2.setTipo(TipoTelefone.valueOf(request.getParameter("tipoTelefone2")));
						telefones.add(tel2);
					}
				}
				
				//Compondo entidade Cliente
				cliente.setNome(nome);
				cliente.setSobrenome(sobrenome);
				cliente.setGenero(TipoGenero.valueOf(genero));
				cliente.setCpf(cpf);
				cliente.setRg(rg);
				cliente.setDtNasc(data);
				cliente.setEmail(email);
				cliente.setSenha(senha);
				cliente.setTipo(TipoUsuario.CLIENTE);
				cliente.addEndereco(endereco);
				cliente.setTelefones(telefones);
				
			}
			if(operacao.contentEquals("ALTERAR")) {
				
				//Variáveis auxiliares
				String dtNasc = request.getParameter("dtNasc");
				Integer idCli = Integer.valueOf(request.getParameter("id_cliente"));
				
				//Instancia de data de nascimento
				LocalDate data = LocalDate.parse(dtNasc, fd.getFormatoView());
				
				/*Obtendo dados da requisição*/
				//Dados Pessoais
				cliente.setId(idCli);
				cliente.setNome(request.getParameter("nome"));
				cliente.setSobrenome(request.getParameter("sobrenome"));
				cliente.setCpf(request.getParameter("cpf"));
				cliente.setRg(request.getParameter("rg")); 
				cliente.setDtNasc(data);
				
			}
		}
		
		return cliente;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
