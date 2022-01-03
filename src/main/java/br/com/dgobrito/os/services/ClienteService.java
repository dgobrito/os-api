package br.com.dgobrito.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dgobrito.os.domain.Cliente;
import br.com.dgobrito.os.domain.Pessoa;
import br.com.dgobrito.os.dto.ClienteDTO;
import br.com.dgobrito.os.exceptions.DataIntegratyViolationException;
import br.com.dgobrito.os.exceptions.ObjectNotFoundException;
import br.com.dgobrito.os.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getSimpleName()));
	}
	
	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if (pessoaService.findByCPF(objDTO.getCpf()) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado"); 			
		}
		
		return repository.save(new Cliente(objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));		
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
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
		Cliente obj = findById(id);
		
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço e não pode ser deletado!");			
		}
		
		repository.deleteById(id);		
	}
}
