package br.com.financeiro.api.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
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

	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private EntryRepository entryRepository;

	public List<Category> findAll() {
		try {
			return repository.findAll();
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Category save(Category category) {
		try {
			return repository.save(category);
		} catch (DataAccessException e) {
			throw new DataAccessResourceFailureException("erro ao acessar banco de dados", e.getCause());
		}

	}

	public Category findOne(Long id) {
		Optional<Category> categoryOptional = repository.findById(id);

		if (categoryOptional.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}

		return categoryOptional.get();
	}

	public void delete(Long id) {
		try {
			Category searchedCategory = findOne(id);

			entryRepository.findAll().stream().forEach(eachEntry -> {
				if (eachEntry.getCategory().getId().equals(searchedCategory.getId())) {
					throw new DataAccessResourceFailureException(
							"Categoria não pode ser excluída pois pertence a um lançamento.");
				}
			});

			repository.delete(searchedCategory);
		} catch (DataAccessException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			throw new DataAccessResourceFailureException("erro ao conectar ao banco de dados");
		}
	}

	public Category update(Long id, Category category) {
		try {
			Category savedCategory = findOne(id);
			BeanUtils.copyProperties(category, savedCategory, "id");

			return repository.save(savedCategory);
		} catch (DataAccessException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			throw new DataAccessResourceFailureException("erro ao conectar ao banco de dados");
		}
	}

}