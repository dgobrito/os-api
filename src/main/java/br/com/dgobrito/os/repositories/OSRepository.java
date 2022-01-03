package br.com.dgobrito.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dgobrito.os.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer> {

}
