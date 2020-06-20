package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tbl_compra")
public class Compra {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="compra_sequence")
	@SequenceGenerator(name = "compra_sequence", sequenceName = "compra_seq", initialValue = 1000000, allocationSize = 1)
	@Column(name="com_in_id")
    private Long id;
	
	@NotNull(message="A compra deve ter um ou mais itens")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="tbl_compra_item", 
		joinColumns = @JoinColumn(name="coi_com_in_id"))
	@Column(name="coi_com_in_id_produto")
	private List<ItensCompra> itens;
	
	@ManyToMany
	@JoinTable(name="tbl_compra_cupom",joinColumns = 
	{@JoinColumn(name="coc_compra_id")}, inverseJoinColumns = 
	{@JoinColumn(name="coc_cupom_id")})
	private List<CupomDesconto> cupons;	
	
	@Column(name="com_st_situacao")
	@NotNull
	@Enumerated(EnumType.STRING)
    private SituacaoCompra situacao;
	
	@Column(name="com_in_cliente")
	@NotNull(message="A compra deve estar vinculada á um cliente")
	private Integer idCliente;
	
	@Column(name="com_in_end_entrega")
	@NotNull(message="Obrigatória a inserção de um endereço de entrega")
	private Integer idEndereco;
	
	@Column(name="com_mo_total")
	@NotNull(message="A compra deve ter um valor")
	@Min(value = 0)
    private BigDecimal total;
	
	@Column(name="com_mo_frete")
    private BigDecimal frete;
	
	@Column(name="com_bo_ativa", columnDefinition = "boolean default 'true'")
    private Boolean ativo = true;
	
	@Column(name="com_dt_data_compra")
	@NotNull(message="A compra deve ter uma data de realização")
	@DateTimeFormat
	private Calendar dataCompra;
	
	@Transient
	private BigDecimal valorDescontos;
	
	@Transient
	private BigDecimal valorItens;

	//Methods
	/**
	 * Método utilizado para validar o pagamento de uma compra com um status de pagamento aleatório
	 */
	public void validarPagamento() {
		
		Random random = new Random();
		
		Integer numeroAleatorio = random.nextInt(100);
		Integer taxaDeAceitacao = 80;
		
		if(numeroAleatorio <= taxaDeAceitacao) {
			this.situacao = SituacaoCompra.PAGAMENTO_APROVADO;
		}else {
			this.situacao = SituacaoCompra.PAGAMENTO_PENDENTE;
		}
		
	}
	
	/**
	 * Método utilizado para efetuar o cancelamento de uma compra
	 * @return true caso o status atual da compra seja passível de cancelamento
	 */
	public boolean cancelar() {
		
		if(situacao != null) {
			if((situacao.equals(SituacaoCompra.CANCELADO)) || (situacao.equals(SituacaoCompra.ENTREGUE)) || (situacao.equals(SituacaoCompra.PAGAMENTO_PENDENTE))){
				return false;
			}
		}
		
		situacao = SituacaoCompra.CANCELADO;
		return true;
	}
	
	/**
	 * Função responsável por validar a troca, garantindo que os itens de troca estejam presentes na compra e não sejam maiores do que a quantidade original da compra
	 * @param itens Lista dos itens selecionados para a troca
	 * @return true caso o processo ocorra normalmente ou false no caso de um produto não estar presente na compra ou apresentar quantidade maior que a presente na compra
	 */
	public boolean validarTroca(List<ItensCompra> itensTroca) {
		
		//O pedido já foi entregue ?
		if(!situacao.equals(SituacaoCompra.ENTREGUE)) {
			return false;
		}
		
		//Criando vetor para validação da existÊncia dos itens
		boolean validaItens[]  = new boolean[itensTroca.size()]; 
		
		//Inicializando todas as posições com 'false'
		Arrays.fill(validaItens, false);
		
		//Percorrendo lista de itens originais da compra
		for (int i=0; i < this.itens.size(); i++) {
			
			//Percorrendo itens cuja troca foi solicitada
			for (int j=0; j < itensTroca.size(); j++) {	
				
				//Item existe ?
				if(this.itens.get(i).getProduto().getId().equals(itensTroca.get(j).getProduto().getId())){
					
					//Quantidade é válida ?
					if(this.itens.get(i).getQuantidade() < itensTroca.get(j).getQuantidade()) {
						return false;
					}
					
					validaItens[j] = true;
				}
			}//.for_itensTroca
		}//.for_itensCompra
		
		//Verifica se todos os itens selecionados para a troca estão presentes na compra
		for(int k=0; k < validaItens.length; k++){
			
			if(!validaItens[k]) {
				return false;
			}
		}
		
		situacao = SituacaoCompra.TROCA_SOLICITADA;
		return true;
	}

	/**
	 * Método responsável por efetuar a conclusão de uma troca, gerando o cupom de desconto equivalente ao valor dos itens envolvidos na troca
	 * @param itens Lista de itens a serem trocados
	 * @return CupomDesconto no valor dos itens selecionados 
	 */
	public CupomDesconto concluirTroca(List<ItensCompra> itens) {
		
		if(validarTroca(itens)) {
				
			BigDecimal valorTroca = new BigDecimal(0);
			
			//Somar valor total dos itens selecionados para troca
			for (ItensCompra itemTroca : itens) {
				valorTroca = valorTroca.add(itemTroca.getTotal());
			}
			
			//Valor válido ?
			if((valorTroca.compareTo(new BigDecimal(0)) > 0) && (valorTroca.compareTo(total) <= 0)) {
				
				this.situacao = SituacaoCompra.TROCA_APROVADA;
				valorTroca = valorTroca.setScale(2, RoundingMode.HALF_EVEN);
				this.total = this.total.subtract(valorTroca);
				
				return new CupomDesconto(idCliente, valorTroca);
			}
		}
		
		this.situacao = SituacaoCompra.TROCA_REJEITADA;
		return null;
	}
	
	public Calendar getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Calendar dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItensCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItensCompra> itens) {
		this.itens = itens;
	}

	public List<CupomDesconto> getCupons() {
		return cupons;
	}

	public void setCupons(List<CupomDesconto> cupons) {
		this.cupons = cupons;
	}

	public SituacaoCompra getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCompra situacao) {
		this.situacao = situacao;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getFrete() {
		
		if(frete == null) {
			frete = new BigDecimal(0);
		}
		
		frete = frete.setScale(2, RoundingMode.HALF_EVEN);
		return frete;
	}

	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}

	public BigDecimal getValorDescontos() {
		
		if(valorDescontos == null) {
			
			BigDecimal desconto = new BigDecimal(0);
			
			for (CupomDesconto cupomDesconto : cupons) {
				desconto = desconto.add(cupomDesconto.getValor());
			}
			
			valorDescontos = desconto.setScale(2, RoundingMode.HALF_EVEN);
		}
		
		return valorDescontos;
	}

	public void setValorDescontos(BigDecimal valorDescontos) {
		this.valorDescontos = valorDescontos;
	}

	public BigDecimal getValorItens() {
		
		if(valorItens == null) {
			
			BigDecimal valor = new BigDecimal(0);
			
			for (ItensCompra item : itens) {
				
				valor = valor.add(item.getTotal());
			}
			
			valorItens = valor.setScale(2, RoundingMode.HALF_EVEN);
		}
		
		return valorItens;
	}

	public void setValorItens(BigDecimal valorItens) {
		this.valorItens = valorItens;
	}
	
}
