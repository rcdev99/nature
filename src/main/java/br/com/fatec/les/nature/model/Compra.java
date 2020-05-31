package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.util.List;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	private Long idCliente;
	
	@Column(name="com_mo_total")
	@NotNull(message="A compra deve ter um valor")
	@Min(value = 0)
    private BigDecimal total;

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

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
