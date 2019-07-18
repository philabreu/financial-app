package br.com.financeiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financeiro.api.exceptionhandler.PessoaInativaException;
import br.com.financeiro.api.model.Entry;
import br.com.financeiro.api.model.Person;
import br.com.financeiro.api.repository.LancamentoRepository;
import br.com.financeiro.api.repository.PessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Iterable<Entry> findAll() {
		return lancamentoRepository.findAll();
	}

	public Entry buscarPorId(Long id) {
		Entry lancamentoBuscado = lancamentoRepository.findOne(id);

		if (lancamentoBuscado == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return lancamentoBuscado;
	}

	public Entry criar(Entry entry) {
		validarPessoa(entry);

		return lancamentoRepository.save(entry);
	}

	public void remover(Long id) {
		Entry lancamentoBuscado = buscarPorId(id);
		lancamentoRepository.delete(lancamentoBuscado);
	}

	public Entry atualizar(Entry entry, Long id) {
		Entry lancamentoCriado = buscarPorId(id);

		if (!(entry.getPerson().equals(lancamentoCriado.getPerson()))) {
			validarPessoa(lancamentoCriado);
		}
		BeanUtils.copyProperties(entry, lancamentoCriado, "id");

		return lancamentoRepository.save(lancamentoCriado);
	}

	private void validarPessoa(Entry entry) {
		Person person = null;

		if (entry.getPerson().getId() != null) {
			person = pessoaRepository.findOne(entry.getPerson().getId());
		}

		if (person == null || person.isInativo()) {
			throw new PessoaInativaException("pessoa inexistente ou inativa.");
		}
	}

}
