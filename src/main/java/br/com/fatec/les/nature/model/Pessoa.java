package br.com.fatec.les.nature.model;

import java.time.LocalDate;

public class Pessoa extends EntidadeDominio {

	private Integer id;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String rg;
	private TipoGenero genero;
	private boolean status;
	private LocalDate dtNasc;
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public LocalDate getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(LocalDate dtNasc) {
		this.dtNasc = dtNasc;
	}
	
	
}
