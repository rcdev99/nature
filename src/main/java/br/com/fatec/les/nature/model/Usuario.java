package br.com.fatec.les.nature.model;

import br.com.fatec.les.nature.util.Criptografia;

public abstract class Usuario extends Pessoa{

	private Integer usr_id;
	private String email;
	private String senha;
	private TipoUsuario tipo;
	private Boolean usr_status;
	
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
	
	/**
	 * Método para obter do banco senhas que já estão criptografadas
	 * @param senha - Salva no objeto a senha obtida do banco
	 */
	public void setSenhaBD(String senha) {
		this.senha = senha;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	public Integer getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(Integer usr_id) {
		this.usr_id = usr_id;
	}
	public Boolean isUsr_status() {
		return usr_status;
	}
	public void setUsr_status(Boolean usr_status) {
		this.usr_status = usr_status;
	}
	
}
