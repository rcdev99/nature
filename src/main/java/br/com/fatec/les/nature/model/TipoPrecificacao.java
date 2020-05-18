package br.com.fatec.les.nature.model;

public enum TipoPrecificacao {

	BANDEJA("ban"),
	GRAMA("g"),
	LITRO("lt"),
	MILIGRAMA("mg"),
	MILILITRO("ml"),
	QUILOGRAMA("kg"),
	UNIDADE("un");

	//Variáveis
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	private TipoPrecificacao(String descricao) {
		this.descricao = descricao;
	}
	
}
