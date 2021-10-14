package br.com.nadsen.desafioeveris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nadsen.desafioeveris.model.Operacao;

public interface OperacoesRepository extends JpaRepository<Operacao, Long> {
	List<Operacao> findByNumConta (int numConta);
}
