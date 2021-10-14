package br.com.nadsen.desafioeveris.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.nadsen.desafioeveris.exceptions.ClienteJaExistenteException;
import br.com.nadsen.desafioeveris.exceptions.ClienteNaoEncontradoException;
import br.com.nadsen.desafioeveris.model.Cliente;
import br.com.nadsen.desafioeveris.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.getByCpf(cliente.getCpf());
		if(!Objects.isNull(clienteExistente)) {
			throw new ClienteJaExistenteException("Cliente já registrado em nosso sistema!");
		}
		return this.clienteRepository.save(cliente);
	}

	public Cliente findByCpf(String cpf) {
		return Optional.ofNullable(clienteRepository.findByCpf(cpf)).get()
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado para o CPF:" + cpf));
	}

	public List<Cliente> findAll(String cpf) {
		List<Cliente> clientes = new ArrayList<>();
		if (cpf == null) {
			clientes = clienteRepository.findAll();
		} else {
			clientes = Arrays.asList(findByCpf(cpf));
		}
		return clientes;
	}

	public Cliente update(String cpf, Cliente clienteAtualizado) {
		Cliente cliente = findByCpf(cpf);
		clienteAtualizado.setId(cliente.getId());
		return clienteRepository.save(clienteAtualizado);
	}

	public Cliente delete(String cpf) {
		Cliente cliente = findByCpf(cpf);
		clienteRepository.delete(cliente);
		return cliente;
	}

	public Cliente obter(String cpf) {
		Cliente cliente = findByCpf(cpf);
		return cliente;
	}

}