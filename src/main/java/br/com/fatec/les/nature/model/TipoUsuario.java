package br.com.fatec.les.nature.model;

public enum TipoUsuario{

	ROLE_DESENVOLVEDOR("Desenvolvedor"),
	ROLE_ADMINISTRATIVO("Administrativo"),
	ROLE_CLIENTE("Cliente"),
	ROLE_FORNECEDOR("Fornecedor");

	private String descricao;
	
	TipoUsuario(String descricao) {
		
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
