package br.com.fatec.les.nature.model;

import java.util.Calendar;

public class Pessoa extends EntidadeDominio {

	private String nome;
	private String sobrenome;
	private String cpf;
	private String rg;
	private TipoGenero genero;
	private boolean status;
	private Calendar dtNasc;
	
	
	//Getters and Setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public TipoGenero getGenero() {
		return genero;
	}
	public void setGenero(TipoGenero genero) {
		this.genero = genero;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Calendar getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(Calendar dtNasc) {
		this.dtNasc = dtNasc;
	}
	
	
}
