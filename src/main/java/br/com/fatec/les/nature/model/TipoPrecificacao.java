package br.com.fatec.les.nature.model;

public enum TipoPrecificacao {

	QUILOGRAMA("kg"),
	MILIGRAMA("mg"),
	GRAMA("g"),
	UNIDADE("un"),
	LITRO("lt"),
	MILILITRO("ml");

	//Variáveis
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	private TipoPrecificacao(String descricao) {
		this.descricao = descricao;
	}
	
}
