package br.com.fatec.les.nature.model;

public enum TipoTelefone {

	CELULAR("celular"),
	RESIDENCIAL("residencial");
	
	private String descricao;
	
	TipoTelefone(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}
}
