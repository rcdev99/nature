package br.com.fatec.les.nature.model;

/**
 * @author USER
 *
 */
public class Estado {

	private String nomeEstado;
	private String sigla;
	private TipoRegiao regiao;
	
		
	//getters and setters
	public String getNomeEstado() {
		return nomeEstado;
	}
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public TipoRegiao getRegiao() {
		return regiao;
	}
	public void setRegiao(TipoRegiao regiao) {
		this.regiao = regiao;
	}

}
