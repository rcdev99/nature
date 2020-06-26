package br.com.fatec.les.nature.dto;

public class DuploRetornoDTO {

	private Boolean situacao;
	private String mensagem;
	
	public DuploRetornoDTO() {
		
	}
	
	public DuploRetornoDTO(Boolean situacao, String msg) {
		this.situacao = situacao;
		this.mensagem = msg;
	}
	
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
