package br.com.dgobrito.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dgobrito.os.domain.Cliente;
import br.com.dgobrito.os.domain.OS;
import br.com.dgobrito.os.domain.Tecnico;
import br.com.dgobrito.os.enums.Prioridade;
import br.com.dgobrito.os.enums.Status;
import br.com.dgobrito.os.repositories.ClienteRepository;
import br.com.dgobrito.os.repositories.OSRepository;
import br.com.dgobrito.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	public void instanciaDB() {
		Tecnico t1 = new Tecnico("Dyego Brito", "920.368.820-05", "(11) 98888-8888");
		Tecnico t2 = new Tecnico("João Brito", "597.908.430-46", "(11) 95555-4444");
		Cliente c1 = new Cliente("Marta Silva", "243.461.640-21", "(11) 97777-6666");
		OS os1 = new OS(Prioridade.ALTA, "Teste Create OS", Status.ANDAMENTO, t1, c1);
		
		t1.getList().add(os1);
		c1.getList().add(os1);
		
		tecnicoRepository.saveAll(Arrays.asList(t1));
		tecnicoRepository.saveAll(Arrays.asList(t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));		
	}
}
