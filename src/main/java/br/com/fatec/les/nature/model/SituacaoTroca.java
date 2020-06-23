package br.com.fatec.les.nature.model;

public enum SituacaoTroca {

	
	AGUARDANDO_ANALISE("Aguardando análise"),
	EM_ANALISE("Em análise"),
	NEGADA("Solicitação negada"),
	APROVADA("Solicitação aprovada");
	
	private String descricao;
	
	SituacaoTroca(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
