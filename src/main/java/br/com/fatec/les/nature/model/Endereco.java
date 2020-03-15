package br.com.fatec.les.nature.model;

public class Endereco extends EntidadeDominio{

	private TipoResidencia tipoResidencia;
	private Logradouro logradouro;
	private int numero;
	private String bairro;
	private String cep;
	private Cidade cidade;
	private String pais;
	private String descricao;
	
	
	
	public String toString() {
		
		String string;
		string = "Tipo de Residência: " + tipoResidencia.getDescricao() + "\nDescrição: " + this.descricao
				+"\nLogradouro: " + logradouro.getTipo() +" " + logradouro.getLogradouro() + "\nCidade: " + cidade.getCidade() + " - " + cidade.getEstado().getSigla()
				+"\nBairro: " + bairro + " CEP: " + cep + " Região: " + cidade.getEstado().getRegiao(); 
		
		return string;
	}
	
	//Getters and Setters
	public TipoResidencia getTipoResidencia() {
		return tipoResidencia;
	}
	public void setTipoResidencia(TipoResidencia tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}
	public Logradouro getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
