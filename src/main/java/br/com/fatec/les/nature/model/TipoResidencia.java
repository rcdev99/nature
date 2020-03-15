package br.com.fatec.les.nature.model;

public enum TipoResidencia {

	APARTAMENTO("apartamento"),
	CASA("casa"),
	SITIO("sitio");

	private String descricao;
	
	TipoResidencia(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}

	
	
}
