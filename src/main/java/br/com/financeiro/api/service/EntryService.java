package br.com.financeiro.api.service;

import java.util.List;

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
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	public Entry findOne(Long id) {
		try {
			Entry lancamentoBuscado = entryRepository.findOne(id);

			if (lancamentoBuscado == null) {
				throw new EmptyResultDataAccessException(1);
			}

			return lancamentoBuscado;
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

	public Entry save(Entry entry) {
		try {
			this.validatePerson(entry);
			
			return entryRepository.save(entry);
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

	public void delete(Long id) {
		try {
			Entry lancamentoBuscado = findOne(id);
			entryRepository.delete(lancamentoBuscado);
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
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	private void validatePerson(Entry entry) {
		Person person = null;

		if (entry.getPerson().getId() != null) {
			person = personRepository.findOne(entry.getPerson().getId());
		}

		if (person == null || person.isInactive()) {
			throw new InactivePersonException("pessoa inexistente ou inativa.");
		}
	}

}
