package br.com.financeiro.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissao")
@EqualsAndHashCode
public class Permission {

	@Id
	@Getter
	private Long id;

	@Getter
	@Setter
	private String descricao;
}
