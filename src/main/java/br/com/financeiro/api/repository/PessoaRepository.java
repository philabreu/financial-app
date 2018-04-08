package br.com.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.financeiro.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
