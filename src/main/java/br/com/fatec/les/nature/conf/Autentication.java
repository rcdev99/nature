package br.com.fatec.les.nature.conf;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fatec.les.nature.dao.UsuarioDAO;
import br.com.fatec.les.nature.model.Cliente;

public class Autentication {
	
	public static boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }
	
	public static String obterUserName() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;    
		
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		return userName;
	}
	
	public static Cliente obterUsuarioLogado() {
		UsuarioDAO DAOUsuario = new UsuarioDAO(); 
		return DAOUsuario.consultaByLogin(Autentication.obterUserName());
	} 
	
}
