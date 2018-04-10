package br.com.financeiro.api.resource;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.financeiro.api.repository.LancamentoRepository;
import br.com.financeiro.api.service.LancamentoService;

public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository lancamentoRepository;

}
