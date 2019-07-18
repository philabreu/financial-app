package br.com.financeiro.api.service;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.model.Category;
import br.com.financeiro.api.repository.CategoryRepository;
import br.com.financeiro.api.repository.LancamentoRepository;

@Service
public class CategoryService {

	private static Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryRepository categoriaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Category criar(Category category) {
		try {
			return categoriaRepository.save(category);
		} catch (DataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new RuntimeException("erro ao conectar ao banco de dados");
		}
		
	}

	public Category buscarPorId(Long id) {
		
		try {
			Category categoriaBuscada = categoriaRepository.findOne(id);
			
			if (categoriaBuscada == null) {
				throw new EmptyResultDataAccessException(1);
			}
			
			return categoriaBuscada;
			
		} catch (DataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new RuntimeException("erro ao conectar ao banco de dados");
		}
	}

	public void remover(Long id) {
		Category categoriaBuscada = buscarPorId(id);

		
		lancamentoRepository
			.findAll()
			.stream()
			.forEach(cadaLancamento -> {
				if (cadaLancamento.getCategory().getId().equals(categoriaBuscada.getId())) {
					throw new RuntimeException("Category não pode ser excluída pois pertence a um lançamento.");
				}
		});

		categoriaRepository.delete(categoriaBuscada);
	}

	public Category atualizar(Long id, Category category) {
		Category categoriaSalva = buscarPorId(id);

		BeanUtils.copyProperties(category, categoriaSalva, "id");

		return categoriaRepository.save(categoriaSalva);
	}

}
