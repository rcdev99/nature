package br.com.fatec.les.nature.model;

public class Telefone extends EntidadeDominio{

	private Integer idTelefone;
	private String ddd;
	private String numero;
	private TipoTelefone tipo;
	private Integer idPessoa;
	
	//Re-escrita de métodos
	public String toString() {
		String string;
		string = "\nDDD: " + this.ddd + " Número: " + this.numero + " Tipo: " + this.tipo; 
		return string;
	}
	
	//Getters and Setters
	public Integer getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Integer idTelefone) {
		this.idTelefone = idTelefone;
	}
	
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
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	
}
