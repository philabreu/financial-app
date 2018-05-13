package br.com.financeiro.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.financeiro.api.model.Categoria;
import br.com.financeiro.api.repository.CategoriaRepositoy;
import br.com.financeiro.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepositoy categoriaRepositoy;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listarTodos() {
		return categoriaRepositoy.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaCriada = categoriaService.criar(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaCriada.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		categoriaService.remover(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		Categoria categoriaBuscada = categoriaService.buscarPorId(id);

		return ResponseEntity.ok().body(categoriaBuscada);

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria, @PathVariable Long id) {
		Categoria categoriaSalva = categoriaService.atualizar(id, categoria);

		return ResponseEntity.ok(categoriaSalva);
	}

}
