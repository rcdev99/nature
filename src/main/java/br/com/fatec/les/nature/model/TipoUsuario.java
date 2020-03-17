package br.com.fatec.les.nature.model;

public enum TipoUsuario {

	ADMINISTRATIVO("Administrativo"),
	CLIENTE("Cliente"),
	FORNECEDOR("Forncedor"),
	DESENVOLVEDOR("Desenvolvedor");

	private String descricao;
	
	TipoUsuario(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}

	
	
}
