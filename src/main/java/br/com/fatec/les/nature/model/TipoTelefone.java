package br.com.fatec.les.nature.model;

public enum TipoTelefone {

	CELULAR("Celular"),
	COMERCIAL("Comercial"),
	RESIDENCIAL("Residencial");
	
	private String descricao;
	
	TipoTelefone(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}
}
