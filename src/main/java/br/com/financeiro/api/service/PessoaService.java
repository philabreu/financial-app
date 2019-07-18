package br.com.financeiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.model.Person;
import br.com.financeiro.api.repository.LancamentoRepository;
import br.com.financeiro.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Person buscarPorId(Long id) {
		Person pessoaBuscada = pessoaRepository.findOne(id);

		if (pessoaBuscada == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return pessoaBuscada;
	}

	public Person criar(Person person) {
		return pessoaRepository.save(person);
	}

	public Person atualizar(Person person, Long id) {
		Person pessoaSalva = buscarPorId(id);

		BeanUtils.copyProperties(person, pessoaSalva, "id");

		return pessoaRepository.save(pessoaSalva);
	}

	public void remover(Long id) {
		Person pessoaBuscada = buscarPorId(id);
		
		lancamentoRepository.findAll().stream().forEach(lancamento -> {
			if(lancamento.getPerson().getId().equals(pessoaBuscada.getId())) {
				throw new RuntimeException("pessoa não pode ser excluída pois pertence a um lançamento.");
			}
		});
		
		pessoaRepository.delete(pessoaBuscada);
	}

	public void atualizarParcial(Long id, Boolean ativo) {
		Person pessoaSalva = buscarPorId(id);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
}
