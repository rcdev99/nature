package br.com.fatec.les.nature.model;

public enum TipoRegiao {

	CENTRO_OESTE("Centro-Oeste"),
	NORDESTE("Nordeste"),
	NORTE("Norte"),
	SUL("Sul"),
	SULDESTE("Suldeste");
	
	

	private String descricao;
	
	TipoRegiao(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
