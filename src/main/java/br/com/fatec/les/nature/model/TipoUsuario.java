package br.com.fatec.les.nature.model;

public enum TipoUsuario {

	DESENVOLVEDOR("Desenvolvedor"),
	ADMINISTRATIVO("Administrativo"),
	CLIENTE("Cliente"),
	FORNECEDOR("Fornecedor");

	private String descricao;
	
	TipoUsuario(String descricao) {
		
		this.descricao = descricao;
		
	}

	public String getDescricao() {
		return descricao;
	}

	
	
}
