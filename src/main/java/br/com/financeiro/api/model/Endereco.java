package br.com.financeiro.api.model;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
public class Endereco {

	@Getter
	private String logradouro;
	
	@Getter
	private String numero;
	
	@Getter
	private String complemento;
	
	@Getter
	private String bairro;
	
	@Getter
	private String cep;
	
	@Getter
	private String cidade;
	
	@Getter
	private String estado;
	
}
