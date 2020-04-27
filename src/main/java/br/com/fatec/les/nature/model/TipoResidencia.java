package br.com.fatec.les.nature.model;

public enum TipoResidencia {

	APARTAMENTO("Apartamento"),
	CASA("Casa"),
	SITIO("Sitio");

	private String descricao;
	
	TipoResidencia(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}

	
	
}
