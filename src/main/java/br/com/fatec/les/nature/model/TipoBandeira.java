package br.com.fatec.les.nature.model;

public enum TipoBandeira {

	MASTERCARD("Mastercard"),
	VISA("Visa");

	private String descricao;
	
	TipoBandeira(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
