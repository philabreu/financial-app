package br.com.financeiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.exceptionhandler.PessoaInativaException;
import br.com.financeiro.api.model.Lancamento;
import br.com.financeiro.api.model.Pessoa;
import br.com.financeiro.api.repository.LancamentoRepository;
import br.com.financeiro.api.repository.PessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Iterable<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}

	public Lancamento buscarPorId(Long id) {
		Lancamento lancamentoBuscado = lancamentoRepository.findOne(id);

		if (lancamentoBuscado == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return lancamentoBuscado;
	}

	public Lancamento criar(Lancamento lancamento) {
		validarPessoa(lancamento);

		return lancamentoRepository.save(lancamento);
	}

	public void remover(Long id) {
		Lancamento lancamentoBuscado = buscarPorId(id);
		lancamentoRepository.delete(lancamentoBuscado);
	}

	public Lancamento atualizar(Lancamento lancamento, Long id) {
		Lancamento lancamentoCriado = buscarPorId(id);

		if (!(lancamento.getPessoa().equals(lancamentoCriado.getPessoa()))) {
			validarPessoa(lancamentoCriado);
		}
		BeanUtils.copyProperties(lancamento, lancamentoCriado, "id");

		return lancamentoRepository.save(lancamentoCriado);
	}

	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;

		if (lancamento.getPessoa().getId() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInativaException("pessoa inexistente ou inativa.");
		}
	}

}
