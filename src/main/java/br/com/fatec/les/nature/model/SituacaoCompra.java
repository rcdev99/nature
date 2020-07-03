package br.com.fatec.les.nature.model;

public enum SituacaoCompra {

	COMPRA_REALIZADA("Compra realizada"),
	PAGAMENTO_PENDENTE("Pagamento pendente"),
	PAGAMENTO_APROVADO("Pagamento aprovado"),
	ENVIADO_A_TRANSPORTADORA("Enviado à transportadora"),
	EM_TRANSPORTE("Em transporte"),
	ENTREGUE("Entregue"),
	TROCA_SOLICITADA("Troca Solicitada"),
	TROCA_APROVADA("Troca Aprovada"),
	TROCA_REJEITADA("Troca Rejeitada"),
	TROCA_EM_ANDAMENTO("Troca em andamento"),
	CLIENTE_NAO_ENCONTRADO("Cliente não Encontrado"),
	DEVOLVIDO("Devolvido"),
	REJEITADO_PELO_CLIENTE("Rejeitado"),
	CANCELADO("Cancelado");

	private String descricao;
	
	SituacaoCompra(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
