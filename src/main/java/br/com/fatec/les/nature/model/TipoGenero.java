package br.com.fatec.les.nature.model;

public enum TipoGenero {

	F("Feminino"),
	M("Masculino"),
	O("Outros");
	
	 public String getDescricao() {
		return descricao;
	}

	private String descricao;
	 
	TipoGenero(String descricao) {
		this.descricao = descricao;
	}
	 
	
	
}
