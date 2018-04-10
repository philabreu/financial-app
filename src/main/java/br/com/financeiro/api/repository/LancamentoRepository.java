package br.com.financeiro.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.financeiro.api.model.Lancamento;

public interface LancamentoRepository extends CrudRepository<Lancamento, Long> {

}
