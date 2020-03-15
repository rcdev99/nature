package br.com.fatec.les.nature.model;

public enum TipoLogradouro {

	AVENIDA("avenida"),
	CHACARA("chácara"), 
	CONDOMINIO("condomínio"), 
	CONJUNTO("conjunto"),
	ESTRADA("estrada"), 
	FAZENDA("fazenda"),
	LOTE("lote"),
	PARQUE("parque"),
	PATIO("pátio"),
	PRACA("praça"),
	RESIDENCIA("residencial"), 
	RODOVIA("rodovia"),
	RUA("rua"),
	SITIO("sítio"),
	TRAVESSA("travessa"),
	TREVO("trevo"), 
	VIADUTO("viaduto"),
	VIELA("viela"),
	VILA("vila");

	 public String getDescricao() {
		return descricao;
	}

	private String descricao;
	 
	TipoLogradouro(String descricao) {
		this.descricao = descricao;
	}
	 
	
	
}
