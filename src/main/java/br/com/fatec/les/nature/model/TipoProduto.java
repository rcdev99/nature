package br.com.fatec.les.nature.model;

public enum TipoProduto {

	FRUTA("Fruta"),
	GRAO("Grãos"),
	LEGUMES("Legumes"),
	SUCO("Suco"),
	VEGETAL("Vegetais");

	//Variáveis
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	TipoProduto(String descricao) {
		this.descricao = descricao;
	}

	
	
	
}
