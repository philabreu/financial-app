package br.com.financeiro.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.financeiro.api.event.RecursoCriadoEvent;
import br.com.financeiro.api.model.Categoria;
import br.com.financeiro.api.repository.CategoriaRepositoy;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepositoy categoriaRepositoy;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listarTodos() {
		return categoriaRepositoy.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaCriada = categoriaRepositoy.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaCriada.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		Categoria categoriaBuscada = categoriaRepositoy.findOne(id);

		if (categoriaBuscada == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(categoriaBuscada);

	}

}
