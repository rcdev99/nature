package br.com.fatec.les.nature;

import java.sql.SQLException;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;
import br.com.fatec.les.nature.model.TipoUsuario;

public class TesteUsuarioDAO {

	public static void main(String[] args) throws SQLException {
		
		Cliente usuario = new Cliente();
		UsuarioDAO DAOUsuario = new UsuarioDAO();
		
		usuario.setEmail("ricardo06@gmail.com");
		usuario.setSenha("ricardo");
		usuario.setTipo(TipoUsuario.FORNECEDOR);
		usuario.setId(1002);
		usuario.setUsr_id(1001);
		
		System.out.println(DAOUsuario.consultaByLogin(usuario.getEmail()).getSenha());
		
		
		

	}

}
