package br.com.financeiro.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String descricao;

	@Column(name = "data_vencimento")
	@Getter
	@Setter
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	@Getter
	@Setter
	private LocalDate dataPagamento;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	private String observacao;

	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private TipoLancamento tipoLancamento;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	@Getter
	@Setter
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	@Getter
	@Setter
	private Pessoa pessoa;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
