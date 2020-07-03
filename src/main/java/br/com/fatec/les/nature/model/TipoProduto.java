package br.com.fatec.les.nature.model;

public enum TipoProduto {

	ESPECIARIA("Especiaria"),
	FRUTA("Fruta"),
	GRAO("Grãos"),
	LEGUMES("Legumes"),
	SUCO("Suco"),
	VEGETAL("Vegetal");

	//Variáveis
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	TipoProduto(String descricao) {
		this.descricao = descricao;
	}

}
