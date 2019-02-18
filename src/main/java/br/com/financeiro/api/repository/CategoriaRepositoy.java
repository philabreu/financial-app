package br.com.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.financeiro.api.model.Categoria;

@Repository
public interface CategoriaRepositoy extends JpaRepository<Categoria, Long> {

}
