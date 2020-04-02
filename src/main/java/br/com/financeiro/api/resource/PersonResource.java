package br.com.financeiro.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import br.com.financeiro.api.event.CreatedResourceEvent;
import br.com.financeiro.api.model.Person;
import br.com.financeiro.api.service.PersonService;

@RestController
@RequestMapping("/pessoa")
public class PersonResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

	@Autowired
	private PersonService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity<?> findAll() {
		LOGGER.info("calling findAll method in PersonResource:");
		try {
			List<Person> peopleList = service.findAll();

			return ResponseEntity.ok(peopleList);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity<?> findOne(@PathVariable Long id) {
		LOGGER.info("calling findOne method in PersonResource:");
		try {
			Person pessoaBuscada = service.findOne(id);

			return ResponseEntity.ok().body(pessoaBuscada);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<?> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		LOGGER.info("calling save method in PersonResource:");
		try {
			Person pessoaCriada = service.save(person);
			publisher.publishEvent(new CreatedResourceEvent(this, response, pessoaCriada.getId()));

			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
	public void delete(@PathVariable Long id) {
		LOGGER.info("calling delete method in PersonResource:");
		try {
			service.delete(id);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Person person) {
		LOGGER.info("calling update method in PersonResource:");
		try {
			Person pessoaSalva = service.update(person, id);

			return ResponseEntity.ok(pessoaSalva);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.OK)
	public void partialUpdate(@PathVariable Long id, @RequestBody Boolean ativo) {
		LOGGER.info("calling partialUpdate method in PersonResource:");
		try {
			service.partialUpdate(id, ativo);
		} catch (HttpClientErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
		} catch (HttpServerErrorException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

}
