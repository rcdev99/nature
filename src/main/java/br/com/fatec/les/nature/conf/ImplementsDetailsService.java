package br.com.fatec.les.nature.conf;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;

@Repository
public class ImplementsDetailsService implements UserDetailsService{

	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Cliente usuario = DAOUsuario.consultaByLogin(username);
		
		if(usuario == null) {
			System.out.println("Usuario " + username + "não encontrado");
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return usuario;
	}

}
