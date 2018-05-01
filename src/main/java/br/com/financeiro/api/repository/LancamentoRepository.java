package br.com.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.financeiro.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
