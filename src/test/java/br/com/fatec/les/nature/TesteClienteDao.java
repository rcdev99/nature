package br.com.fatec.les.nature;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.fatec.les.nature.dao.ClienteDAO;
import br.com.fatec.les.nature.dao.IDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.TipoGenero;
import br.com.fatec.les.nature.model.TipoUsuario;
import br.com.fatec.les.nature.util.FormataData;

@SuppressWarnings("unused")
public class TesteClienteDao {

	public static void main(String[] args) throws SQLException {
		
		ClienteDAO cliDAO = new ClienteDAO();
		Cliente cliente = new Cliente();
		FormataData fd = new FormataData();
//		LocalDate data = LocalDate.parse("08/03/1995", fd.getFormato());
		
		cliente.setNome("Ric");
//		cliente.setSobrenome("Júnior");
//		cliente.setRg("41.934.653-3");
//		cliente.setCpf("439.144.438-04");
//		cliente.setGenero(TipoGenero.M);
//		cliente.setDtNasc(data);
//		cliente.setId(1002);
		
		System.out.println("Retorno: \n" + cliDAO.consultaById(1002));

		
		
	}
	
}
