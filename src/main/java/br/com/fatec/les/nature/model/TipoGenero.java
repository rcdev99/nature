package br.com.fatec.les.nature.model;

public enum TipoGenero {

	F("F"),
	M("M"),
	O("O");
	
	 public String getDescricao() {
		return descricao;
	}

	private String descricao;
	 
	TipoGenero(String descricao) {
		this.descricao = descricao;
	}
	 
	
	
}
