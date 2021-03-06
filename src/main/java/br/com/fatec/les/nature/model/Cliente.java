package br.com.fatec.les.nature.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import br.com.fatec.les.nature.util.Role;

public class Cliente extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<Cartao> cartoes = new ArrayList<Cartao>();
	private List<Telefone> telefones = new ArrayList<Telefone>();
	

	//Re-escrita de métodos
	public String toString() {
		
		String string;
		int nCard = 1; 
		int nEnd = 1;
		int nTel= 1;
		
		string = "Nome: " + super.getNome() + " Sobrenome: " + super.getSobrenome() 
				+ "\nRG: " + super.getRg() + " CPF: " + super.getCpf()
				+ "\nGênero: " + super.getGenero().getDescricao() + " E-mail: " + super.getEmail()
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
				//Exibir os telefones registrados
				for (Telefone telefone: telefones) {
					string = string + "\n\n--Telefone(s): " + nTel+"--\n" + telefone.toString(); 
					nTel++;
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
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public void addTelefone(Telefone telefone) {
		this.telefones.add(telefone);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Role acesso = new Role();
		List<Role> roles = new ArrayList<Role>();
		
		acesso.setNomeRole(this.getTipo().name());
		
		roles.add(acesso);
		
		return (Collection<? extends GrantedAuthority>) roles;
	}

	@Override
	public String getPassword() {
		// Obtém a senha do usuário
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		// Username será o e-mail do usuário
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getAuthority() {
		
		return this.getTipo().name();
	}
}
