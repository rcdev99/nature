package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tbl_troca")
public class Troca {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="troca_sequence")
	@SequenceGenerator(name = "troca_sequence", sequenceName = "troca_seq", initialValue = 10000, allocationSize = 1)
	@Column(name="tca_in_id")
    private Long id;
	
	@NotNull(message="A troca deve ter um ou mais itens")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="tbl_troca_item", 
		joinColumns = @JoinColumn(name="tcai_com_in_id"))
	@Column(name="tcai_com_in_id_produto")
	private List<ItensCompra> itens;
	
	@OneToOne(cascade=CascadeType.ALL , fetch = FetchType.LAZY)
	@JoinColumn(name="tca_in_id_compra", referencedColumnName = "com_in_id")
	private Compra compra;
	
	@Column(name="tca_bo_aprovada" , columnDefinition = "boolean default 'false'")
	private boolean aprovada;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="tca_st_situacao")
	private SituacaoTroca situacao;
	
	@Column(name="tca_st_motivo")
	private String motivo;

	@Column(name="tca_dt_data_solicitacao")
	@NotNull(message="Deve ser preenchida a data de solicitação da troca")
	@DateTimeFormat
	private Calendar dataTroca; 

	@Transient
	private BigDecimal valorTroca;

	//Constructor
	public Troca() {
		
	}
	
	//Construtor padrão para troca
	public Troca(List<ItensCompra> itens, Compra compra,String motivo) {
		
		this.itens = itens;
		this.compra = compra;
		this.motivo = motivo;
		this.situacao = SituacaoTroca.AGUARDANDO_ANALISE;
		
	}
	
	//Methods
	public void aprovarTroca() {
		
		this.situacao = SituacaoTroca.APROVADA;
		this.aprovada = true;
	}
	
	public void negarSolicitacaoDeTroca() {
		
		this.situacao = SituacaoTroca.NEGADA;
		this.aprovada = false;
	}
	
	/**
	 * Gera cupom no valor da troca, caso a mesma não tenha sido aprovada antes
	 * @return
	 */
	public CupomDesconto gerarCupomDeTroca() {
		
		if(!aprovada) {
			String msg = "Cupom gerado em decorrência de solicitação de troca do pedido #" + compra.getId();
			CupomDesconto cupom = new CupomDesconto(compra.getIdCliente(), getValorTroca(), msg);
			
			return cupom;
		}else {
			return null;
		}
		
	}
 
	//Getters and Setter
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

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public boolean isAprovada() {
		return aprovada;
	}

	public void setAprovada(boolean aprovada) {
		this.aprovada = aprovada;
	}

	public SituacaoTroca getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoTroca situacao) {
		this.situacao = situacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	
	
	public Calendar getDataTroca() {
		return dataTroca;
	}

	public void setDataTroca(Calendar dataTroca) {
		this.dataTroca = dataTroca;
	}
	
	public BigDecimal getValorTroca() {
		
		BigDecimal valor = new BigDecimal(0);
		
		for (ItensCompra item : itens) {
			valor = valor.add(item.getTotal());
		}
		
		this.valorTroca = valor.setScale(2, RoundingMode.HALF_EVEN);
		return valorTroca;
	}

	public void setValorTroca(BigDecimal valorTroca) {
		this.valorTroca = valorTroca;
	}
}
