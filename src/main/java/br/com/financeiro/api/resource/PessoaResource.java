package br.com.financeiro.api.resource;

import java.util.List;

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
import br.com.financeiro.api.model.Pessoa;
import br.com.financeiro.api.repository.PessoaRepository;
import br.com.financeiro.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
		Pessoa pessoaBuscada = pessoaService.buscarPorId(id);

		return ResponseEntity.ok().body(pessoaBuscada);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaCriada = pessoaService.criar(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaCriada.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
	public void remover(@PathVariable Long id) {
		pessoaService.remover(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(pessoa, id);

		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{id}/ativo")
	public void atualizarParcial(@PathVariable Long id, @RequestBody Boolean ativo) {
		pessoaService.atualizarParcial(id, ativo);
	}

}
