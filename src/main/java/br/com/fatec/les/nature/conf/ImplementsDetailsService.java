package br.com.fatec.les.nature.conf;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;

@Repository
@Transactional
public class ImplementsDetailsService implements UserDetailsService{

	UsuarioDAO DAOUsuario = new UsuarioDAO();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Cliente usuario = DAOUsuario.consultaByLogin(username);
		
		if(usuario == null) {
			System.out.println("Usuario " + username + "não encontrado");
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}

}
