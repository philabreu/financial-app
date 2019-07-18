package br.com.financeiro.api.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Getter;

@Embeddable
public class Address {

	@Getter
	@Size(max = 30)
	private String logradouro;

	@Getter
	@Size(max = 30)
	private String numero;

	@Getter
	@Size(max = 30)
	private String complemento;

	@Getter
	@Size(max = 30)
	private String bairro;

	@Getter
	@Size(max = 30)
	private String cep;

	@Getter
	@Size(max = 30)
	private String cidade;

	@Getter
	@Size(max = 30)
	private String estado;

}
