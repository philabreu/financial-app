package br.com.financeiro.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import br.com.financeiro.api.event.RecursoCriadoEvent;
import br.com.financeiro.api.model.Entry;
import br.com.financeiro.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class EntryEndpoint {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public Iterable<Entry> listarTodos() {
		return lancamentoService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public ResponseEntity<Entry> buscarPorId(@PathVariable Long id) {
		Entry lancamentoBuscado = lancamentoService.buscarPorId(id);

		return ResponseEntity.ok().body(lancamentoBuscado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Entry> criar(@Valid @RequestBody Entry entry, HttpServletResponse response) {
		Entry lancamentoCriado = lancamentoService.criar(entry);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoCriado.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoCriado);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO')")
	public void remover(@PathVariable Long id) {
		lancamentoService.remover(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Entry> atualizar(@Valid @RequestBody Entry entry, @PathVariable Long id) {
		try {
			Entry lancamentoAtualizado = lancamentoService.atualizar(entry, id);

			return ResponseEntity.ok(lancamentoAtualizado);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
