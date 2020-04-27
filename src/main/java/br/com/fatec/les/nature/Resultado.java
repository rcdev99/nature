package br.com.fatec.les.nature;

import java.util.List;

import br.com.fatec.les.nature.model.EntidadeDominio;

public class Resultado extends EntidadeDominio{

	private String msg;
	private List<EntidadeDominio> entidades;
	
	public List<EntidadeDominio> getEntidades() {
		return entidades;
	}
	
	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	} 
	
	
}
