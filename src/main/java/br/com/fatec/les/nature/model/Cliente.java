package br.com.fatec.les.nature.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{

	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<Cartao> cartoes = new ArrayList<Cartao>();
	
	//Re-escrita de métodos
	public String toString() {
		
		String string;
		int nCard = 1; 
		int nEnd = 1;
		
		string = "Nome: " + super.getNome() + " Sobrenome: " + super.getSobrenome() 
				+ "\nRG: " + super.getRg() + " CPF: " + super.getCpf()
				+ "\nGênero: " + super.getGenero() + " E-mail: " + super.getEmail()
				+ "\nTipo de usuário: " + super.getTipo() + "\n";
				//Exibir os cartões registrados
				for (Cartao cartao : cartoes) {
					string = string + "\n\n--Cartão: " + nCard + "--\n" + cartao.toString();
					nCard++;
				}
				//Exibir os endereços registrados
				for (Endereco endereco : enderecos) {
					string = string + "\n\n--Endereco: " + nEnd +"--\n" + endereco.toString(); 
					nEnd++;
				}
		return string;
	}
	
	//Getters and Setters
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	
	public void addEndereco(Endereco endereco) {
		this.enderecos.add(endereco);
	}
	
	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}

	public void addCartao(Cartao cartao) {
		this.cartoes.add(cartao);
	}
	
}
