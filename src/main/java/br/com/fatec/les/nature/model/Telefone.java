package br.com.fatec.les.nature.model;

public class Telefone extends EntidadeDominio{

	private String ddd;
	private String numero;
	private TipoTelefone tipo;
	
	//Re-escrita de métodos
	public String toString() {
		String string;
		string = "\nDDD: " + this.ddd + " Número: " + this.numero + " Tipo: " + this.tipo; 
		return string;
	}
	
	//Getters and Setters
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public TipoTelefone getTipo() {
		return tipo;
	}
	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}
	
}
