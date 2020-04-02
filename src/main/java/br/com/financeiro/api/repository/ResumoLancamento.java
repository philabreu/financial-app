package br.com.financeiro.api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.financeiro.api.model.EntryType;

public class ResumoLancamento {

	private Long id;

	private String description;

	private LocalDate dataVencimento;

	private LocalDate dataPagamento;

	private BigDecimal value;

	private EntryType entryType;

	private String category;

	private String person;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal valor) {
		this.value = valor;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String categoria) {
		this.category = categoria;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String pessoa) {
		this.person = pessoa;
	}

	public Long getId() {
		return id;
	}

	public ResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, EntryType entryType, String categoria, String pessoa) {
		this.id = id;
		this.description = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.value = valor;
		this.entryType = entryType;
		this.category = categoria;
		this.person = pessoa;
	}

}
