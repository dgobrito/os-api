package br.com.dgobrito.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dgobrito.os.domain.Pessoa;
import br.com.dgobrito.os.domain.Tecnico;
import br.com.dgobrito.os.dto.TecnicoDTO;
import br.com.dgobrito.os.exceptions.DataIntegratyViolationException;
import br.com.dgobrito.os.exceptions.ObjectNotFoundException;
import br.com.dgobrito.os.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getSimpleName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		if (pessoaService.findByCPF(objDTO.getCpf()) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado"); 			
		}
		
		return repository.save(new Tecnico(objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		Pessoa objCPF = pessoaService.findByCPF(objDTO.getCpf());
		
		if ((objCPF != null) && (objCPF.getId() != id)) {
			throw new DataIntegratyViolationException("CPF já cadastrado");			
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço e não pode ser deletado!");			
		}
		
		repository.deleteById(id);		
	}
}
