package br.com.fatec.les.nature.dto;

import br.com.fatec.les.nature.model.Endereco;

public class EnderecoDTO {

	private Integer id;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String tipoResidencia;
	private String numero;
	
	//Constructor	
	public EnderecoDTO(Integer id, String estado, String cidade, String bairro, String logradouro, String tipoResidencia,
			String numero) {
		super();
		this.id = id;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.tipoResidencia = tipoResidencia;
		this.numero = numero;
	}
	
	public EnderecoDTO(String estado, String cidade, String bairro, String logradouro, String tipoResidencia,String numero) {
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.tipoResidencia = tipoResidencia;
		this.numero = numero;
	}
	
	public EnderecoDTO() {
	
	}
	
	//Methods
	public void construirDTOAtravesDeEndereco(Endereco endereco) {
		
		this.id = endereco.getId_endereco();
		this.estado = endereco.getCidade().getEstado().getSigla();
		this.cidade = endereco.getCidade().getCidade();
		this.bairro = endereco.getBairro();
		this.logradouro = endereco.getLogradouro().getLogradouro();
		this.tipoResidencia = endereco.getTipoResidencia().getDescricao();
		this.numero = endereco.getNumero().toString(); 

	}
		
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getTipoResidencia() {
		return tipoResidencia;
	}
	public void setTipoResidencia(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
