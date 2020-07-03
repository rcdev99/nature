package br.com.fatec.les.nature;

import java.sql.SQLException;

import br.com.fatec.les.nature.dao.EnderecoDAO;
import br.com.fatec.les.nature.model.Cidade;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.Estado;
import br.com.fatec.les.nature.model.Logradouro;
import br.com.fatec.les.nature.model.TipoLogradouro;
import br.com.fatec.les.nature.model.TipoRegiao;
import br.com.fatec.les.nature.model.TipoResidencia;

public class TesteEnderecoDAO {

	public static void main(String[] args) throws SQLException {
		
		Estado estado = new Estado();
		Cidade cidade = new Cidade();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		EnderecoDAO DAOendereco = new EnderecoDAO();
		
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
		endereco.setDescricao("Comercial");
		endereco.setBairro("Vila Mogilar");
		endereco.setCep("08870-245");
		endereco.setCidade(cidade);
		endereco.setLogradouro(logradouro);
		endereco.setNumero(94);
		endereco.setPais("Brasil");
		endereco.setTipoResidencia(TipoResidencia.SITIO);
		endereco.setIdPessoa(1002);
		//endereco.setId_endereco(2);
		
		System.out.println(DAOendereco.enderecoMaisRecente(1002).getId_endereco());
	}

}
