package br.com.nadsen.desafioeveris.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nadsen.desafioeveris.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	Optional<Conta> findByNumConta(int i);

	Conta getByNumConta(int i);
	
	List<Conta> findByClienteId (Long id);

}
