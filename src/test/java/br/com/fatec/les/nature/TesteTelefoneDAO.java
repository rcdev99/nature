package br.com.fatec.les.nature;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.nature.dao.TelefoneDAO;
import br.com.fatec.les.nature.model.EntidadeDominio;
import br.com.fatec.les.nature.model.Telefone;
import br.com.fatec.les.nature.model.TipoTelefone;

public class TesteTelefoneDAO {

	public static void main(String[] args) throws SQLException {
		
				//Instanciando lista
				List<EntidadeDominio> telefones = new ArrayList<>();
				
				TelefoneDAO DAOTelefone = new TelefoneDAO();
				
				//Instancias de telefone
				Telefone telefone1 = new Telefone();
				telefone1.setDdd("11");
				telefone1.setNumero("99919-1999");
				telefone1.setTipo(TipoTelefone.CELULAR);
				telefone1.setIdPessoa(1002);
				
				Telefone telefone2 = new Telefone();
//				telefone2.setDdd("11");
//				telefone2.setNumero("4747-7474");
				telefone2.setTipo(TipoTelefone.RESIDENCIAL);
//				telefone2.setIdPessoa(1002);
				
				Telefone telefone3 = new Telefone();
				telefone3.setDdd("21");
				telefone3.setNumero("4799-0000");
				telefone3.setTipo(TipoTelefone.COMERCIAL);
				telefone3.setIdPessoa(1002);
				telefone3.setIdTelefone(5);
				
				//Exibição dos registros
				telefones = DAOTelefone.consultaByPessoa(1002);
				
				for (EntidadeDominio telefone: telefones) {
				
					System.out.println(telefone);
					
				}
					
	}

}
