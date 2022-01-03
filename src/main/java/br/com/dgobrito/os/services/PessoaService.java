package br.com.dgobrito.os.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dgobrito.os.domain.Pessoa;
import br.com.dgobrito.os.repositories.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public Pessoa findByCPF(String cpf) {
		Pessoa obj = repository.findByCPF(cpf);
		
		return obj != null ? obj : null;
	}	
}
