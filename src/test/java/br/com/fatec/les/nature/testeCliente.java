package br.com.fatec.les.nature;

import java.util.Calendar;
import java.util.Date;

import br.com.fatec.les.nature.model.Cartao;
import br.com.fatec.les.nature.model.Cidade;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.Estado;
import br.com.fatec.les.nature.model.Logradouro;
import br.com.fatec.les.nature.model.TipoBandeira;
import br.com.fatec.les.nature.model.TipoGenero;
import br.com.fatec.les.nature.model.TipoLogradouro;
import br.com.fatec.les.nature.model.TipoRegiao;
import br.com.fatec.les.nature.model.TipoResidencia;
import br.com.fatec.les.nature.model.TipoUsuario;

public class testeCliente {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Logradouro logradouro = new Logradouro();
		Cliente cliente = new Cliente();
		Calendar dt = Calendar.getInstance();
		
		
		//Estado
		estado.setNomeEstado("São Paulo");
		estado.setRegiao(TipoRegiao.SULDESTE);
		estado.setSigla("SP");
		
		//Cidade
		cidade.setCidade("Mogi das Cruzes");
		cidade.setEstado(estado);
		
		//Logradouro
		logradouro.setLogradouro("Antonio Bóz Vidal");
		logradouro.setTipo(TipoLogradouro.AVENIDA);
		
		
		//Cliente
		cliente.setNome("Ricardo");
		cliente.setSobrenome("Floriano");
		cliente.setCpf("439.244.438-04");
		cliente.setRg("41.934.653-3");
		cliente.setDtNasc(dt);
		cliente.setEmail("ric@ricardo");
		cliente.setGenero(TipoGenero.M);
		cliente.setTipo(TipoUsuario.DESENVOLVEDOR);
		
		//Endereço 1
		Endereco endereco1 = new Endereco();
		endereco1.setDescricao("Minha casa");
		endereco1.setBairro("Jardim Bela Vista");
		endereco1.setCep("08820-235");
		endereco1.setCidade(cidade);
		endereco1.setLogradouro(logradouro);
		endereco1.setNumero(94);
		endereco1.setPais("Brasil");
		endereco1.setTipoResidencia(TipoResidencia.APARTAMENTO);

		//Endereço 2
		Endereco endereco2 = new Endereco();
		endereco2.setDescricao("Comercial");
		endereco2.setBairro("Vila Industrial");
		endereco2.setCep("08888-888");
		endereco2.setCidade(cidade);
		endereco2.setLogradouro(logradouro);
		endereco2.setNumero(999);
		endereco2.setPais("Brasil");
		endereco2.setTipoResidencia(TipoResidencia.CASA);
						
		//Instancia de data para atribuir ao vencimento do cartão
		Date d = new Date("03/04/2020");
		Calendar data = Calendar.getInstance();
		data.setTime(d);
		
		//Cartao1
		Cartao cartao1 = new Cartao();
		cartao1.setTitular("Ricardo");
		cartao1.setNumCartao("0000.0000.0000.0000");
		cartao1.setBandeira(TipoBandeira.MASTERCARD);
		cartao1.setCvv(123);
		cartao1.setDtVenc(data);		

		//Cartão2
		Cartao cartao2 = new Cartao();
		cartao2.setTitular("Odracir");
		cartao2.setNumCartao("0000.0000.0000.0000");
		cartao2.setBandeira(TipoBandeira.VISA);
		cartao2.setCvv(321);
		cartao2.setDtVenc(data);
		
		//Atribuindo Endreços ao Cliente
		cliente.addEndereco(endereco1);
		cliente.addEndereco(endereco2);
		
		//Atribuindo Cartões ao Cliente
		cliente.addCartao(cartao1);
		cliente.addCartao(cartao2);
		
		System.out.println(cliente);
		
	}
	
	
}
