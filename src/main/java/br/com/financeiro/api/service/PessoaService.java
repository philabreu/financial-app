package br.com.financeiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.model.Pessoa;
import br.com.financeiro.api.repository.LancamentoRepository;
import br.com.financeiro.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Pessoa buscarPorId(Long id) {
		Pessoa pessoaBuscada = pessoaRepository.findOne(id);

		if (pessoaBuscada == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return pessoaBuscada;
	}

	public Pessoa criar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Pessoa atualizar(Pessoa pessoa, Long id) {
		Pessoa pessoaSalva = buscarPorId(id);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");

		return pessoaRepository.save(pessoaSalva);
	}

	public void remover(Long id) {
		Pessoa pessoaBuscada = buscarPorId(id);
		
		lancamentoRepository.findAll().stream().forEach(lancamento -> {
			if(lancamento.getPessoa().getId().equals(pessoaBuscada.getId())) {
				throw new RuntimeException("pessoa não pode ser excluída pois pertence a um lançamento.");
			}
		});
		
		pessoaRepository.delete(pessoaBuscada);
	}

	public void atualizarParcial(Long id, Boolean ativo) {
		Pessoa pessoaSalva = buscarPorId(id);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
}
