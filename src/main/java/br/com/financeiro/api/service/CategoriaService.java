package br.com.financeiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.model.Categoria;
import br.com.financeiro.api.repository.CategoriaRepositoy;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepositoy categoriaRepository;
	
	public Categoria criar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria buscarPorId(Long id) {
		Categoria categoriaBuscada = categoriaRepository.findOne(id);

		if (categoriaBuscada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoriaBuscada;
	}

	public void remover(Long id) {
		Categoria categoriaBuscada = buscarPorId(id);
		categoriaRepository.delete(categoriaBuscada);
	}

	public Categoria atualizar(Long id, Categoria categoria) {
		Categoria categoriaSalva = buscarPorId(id);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		
		return categoriaRepository.save(categoriaSalva);
	}

}
