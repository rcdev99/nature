package br.com.fatec.les.nature;

import br.com.fatec.les.nature.model.*;;

public class testeEndereco {

	public static void main(String[] args) {
		
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		
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
		
		//Endereço
		endereco.setDescricao("Minha casa");
		endereco.setBairro("Jardim Bela Vista");
		endereco.setCep("08820-235");
		endereco.setCidade(cidade);
		endereco.setLogradouro(logradouro);
		endereco.setNumero(94);
		endereco.setPais("Brasil");
		endereco.setTipoResidencia(TipoResidencia.APARTAMENTO);
		
		System.out.println(endereco);

	}

}
