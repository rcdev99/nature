package br.com.fatec.les.nature.model;

public class Endereco extends EntidadeDominio{

	private Integer id_endereco;
	private TipoResidencia tipoResidencia;
	private Logradouro logradouro;
	private Integer numero;
	private String bairro;
	private String cep;
	private Cidade cidade;
	private String pais;
	private String descricao;
	private Integer idPessoa;
	
	public String toString() {
		
		String string;
		string = "Tipo de Residência: " + tipoResidencia.getDescricao() + "\nDescrição: " + this.descricao
				+"\nLogradouro: " + logradouro.getTipo() +" " + logradouro.getLogradouro() + "\nCidade: " + cidade.getCidade() + " - " + cidade.getEstado().getSigla()
				+"\nBairro: " + bairro + " CEP: " + cep + " Região: " + cidade.getEstado().getRegiao(); 
		
		return string;
	}
	
	public Integer getId_endereco() {
		return id_endereco;
	}

	public void setId_endereco(Integer id_endereco) {
		this.id_endereco = id_endereco;
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
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
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
	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	
}
