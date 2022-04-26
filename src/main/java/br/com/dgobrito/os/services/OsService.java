package br.com.dgobrito.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dgobrito.os.domain.Cliente;
import br.com.dgobrito.os.domain.OS;
import br.com.dgobrito.os.domain.Tecnico;
import br.com.dgobrito.os.dto.OSDTO;
import br.com.dgobrito.os.enums.Status;
import br.com.dgobrito.os.exceptions.ObjectNotFoundException;
import br.com.dgobrito.os.repositories.OSRepository;

@Service
public class OsService {
	
	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getSimpleName()));
	}
	
	public List<OS> findAll() {
		return repository.findAll();
	}

	public OS create(OSDTO dto) {
		OS entity = fromDTO(dto);
		return repository.save(entity);
	}
	
	public OS update(Integer id, @Valid OSDTO obj) {
		findById(id);
		OS entity = fromDTO(obj);
		return repository.save(entity);
	}	
	
	private OS fromDTO(OSDTO dto) {
		OS newObj = new OS();
		newObj.setId(dto.getId());
		newObj.setObservacoes(dto.getObservacoes());
		newObj.setPrioridade(dto.getPrioridade());
		newObj.setStatus(dto.getStatus());
		
		Tecnico tec = tecnicoService.findById(dto.getTecnico());
		Cliente cli = clienteService.findById(dto.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if (newObj.getStatus().getCodigo().equals(Status.ENCERRADO.getCodigo())) {
			newObj.setDataFechamento(LocalDateTime.now());			
		}
		
		return newObj;
	}
}
