package br.com.fatec.les.nature.model;

public enum TipoCupom {


	PROMOCIONAL("Promocional"),
	TROCA("Troca");
	
	 public String getDescricao() {
		return descricao;
	}

	private String descricao;
	 
	TipoCupom(String descricao) {
		this.descricao = descricao;
	}
}
