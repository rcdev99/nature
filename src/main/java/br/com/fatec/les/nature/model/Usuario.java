package br.com.fatec.les.nature.model;

import br.com.fatec.les.nature.util.Criptografia;

public class Usuario extends Pessoa{

	private String email;
	private String senha;
	private TipoUsuario tipo;
	
	//Getters and Setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		
		return senha;
	}
	public void setSenha(String senha) {
		
		Criptografia util = new Criptografia();
		this.senha = util.criptografar(senha);
	}
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
}
