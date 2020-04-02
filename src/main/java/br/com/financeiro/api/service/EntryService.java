package br.com.financeiro.api.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import br.com.financeiro.api.exceptionhandler.InactivePersonException;
import br.com.financeiro.api.model.Entry;
import br.com.financeiro.api.model.Person;
import br.com.financeiro.api.repository.EntryRepository;
import br.com.financeiro.api.repository.PersonRepository;

@Service
public class EntryService {

	private static Logger LOGGER = LoggerFactory.getLogger(EntryService.class);

	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private PersonRepository personRepository;

	public List<Entry> findAll() {
		try {
			List<Entry> entryList = entryRepository.findAll();

			return entryList;
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Entry findOne(Long id) {
		try {
			Optional<Entry> searchedEntry = entryRepository.findById(id);

			if (searchedEntry.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}

			return searchedEntry.get();
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Entry save(Entry entry) {
		try {
			validatePerson(entry);

			return entryRepository.save(entry);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public void delete(Long id) {
		try {
			Entry lancamentoBuscado = findOne(id);
			entryRepository.delete(lancamentoBuscado);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Entry update(Entry entry, Long id) {
		try {
			Entry lancamentoCriado = findOne(id);

			if (!(entry.getPerson().equals(lancamentoCriado.getPerson()))) {
				validatePerson(lancamentoCriado);
			}
			BeanUtils.copyProperties(entry, lancamentoCriado, "id");

			return entryRepository.save(lancamentoCriado);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	private void validatePerson(Entry entry) {
		Optional<Person> person = null;

		if (entry.getPerson().getId() != null) {
			person = personRepository.findById(entry.getPerson().getId());
		}

		if (person.isEmpty() || person.get().isInactive()) {
			throw new InactivePersonException("pessoa inexistente ou inativa.");
		}
	}

}
