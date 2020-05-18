package br.com.fatec.les.nature.util;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	
	/**
	 * Serial version to Role
	 */
	private static final long serialVersionUID = 1L;
	private String nomeRole;
	
	@Override
	public String getAuthority() {
		
		return this.nomeRole;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
}
