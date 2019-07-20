package br.com.financeiro.api.service;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import br.com.financeiro.api.model.Category;
import br.com.financeiro.api.repository.CategoryRepository;
import br.com.financeiro.api.repository.EntryRepository;

@Service
public class CategoryService {

	private static Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private EntryRepository entryRepository;

	public List<Category> findAll() {
		try {
			List<Category> categoriesList = repository.findAll();

			return categoriesList;
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Category save(Category category) {
		try {
			return repository.save(category);
		} catch (DataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new RuntimeException("erro ao conectar ao banco de dados");
		}

	}

	public Category findOne(Long id) {
		try {
			Category categoriaBuscada = repository.findOne(id);

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
		try {
			Category categoriaBuscada = findOne(id);

			entryRepository.findAll().stream().forEach(cadaLancamento -> {
				if (cadaLancamento.getCategory().getId().equals(categoriaBuscada.getId())) {
					throw new RuntimeException("Categoria não pode ser excluída pois pertence a um lançamento.");
				}
			});

			repository.delete(categoriaBuscada);
		} catch (DataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new RuntimeException("erro ao conectar ao banco de dados");
		}
	}

	public Category update(Long id, Category category) {
		try {
			Category categoriaSalva = findOne(id);
			BeanUtils.copyProperties(category, categoriaSalva, "id");

			return repository.save(categoriaSalva);
		} catch (DataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new RuntimeException("erro ao conectar ao banco de dados");
		}
	}

}
