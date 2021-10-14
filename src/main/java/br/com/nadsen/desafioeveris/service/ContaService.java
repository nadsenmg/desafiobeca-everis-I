package br.com.nadsen.desafioeveris.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.nadsen.desafioeveris.controller.request.ContaRequest;
import br.com.nadsen.desafioeveris.evento.Evento;
import br.com.nadsen.desafioeveris.exceptions.ClienteObrigatorioException;
import br.com.nadsen.desafioeveris.exceptions.ContaNaoEncontradaException;
import br.com.nadsen.desafioeveris.model.Cliente;

import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.enums.TipoEvento;
import br.com.nadsen.desafioeveris.repository.ContaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {

	private final ClienteService clienteService;
	private final ContaRepository contaRepository;
	private final ModelMapper modelMapper;
	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Transactional
	public Conta save(ContaRequest contaRequest) {
		Conta conta = modelMapper.map(contaRequest, Conta.class);
		if (Objects.isNull(conta.getCliente())) {
			throw new ClienteObrigatorioException("Cliente obrigatório para cadastrar uma conta!");
		}
		verificarClienteExistenteEmConta(conta.getCliente().getCpf());
		Cliente cliente = clienteService.obter(conta.getCliente().getCpf());
		conta.setCliente(cliente);

		try {
			Evento evento = modelMapper.map(conta, Evento.class);
			evento.setTipoEvento(TipoEvento.CONTA_CRIADA);
			this.kafkaTemplate.send("CONTROLE_SAQUES", String.valueOf(conta.getNumConta()), objectMapper
					.writeValueAsString(new Evento(evento.getTipoEvento(), conta.getNumConta(), conta.getTipoConta(),
							LocalDateTime.now().toString())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return contaRepository.save(conta);
	}

	public Double getSaldo(Integer numConta) {
		Conta conta = contaRepository.getByNumConta(numConta);
		return conta.getSaldo();
	}

	public Conta findByNumConta(Integer numConta) {
		return Optional.ofNullable(contaRepository.findByNumConta(numConta)).get()
				.orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));
	}

	public List<Conta> findAll(Integer numConta) {
		List<Conta> contas = new ArrayList<>();
		if (Objects.isNull(numConta)) {
			contas = contaRepository.findAll();
		} else {
			contas = Arrays.asList(findByNumConta(numConta));
		}
		return contas;
	}

	public Conta update(Integer numConta, Conta contaAtualizado) {
		Conta conta = findByNumConta(numConta);
		contaAtualizado.setId(conta.getId());
		return contaRepository.save(contaAtualizado);
	}

	public Conta delete(Integer numConta) {
		Conta conta = findByNumConta(numConta);
		this.contaRepository.delete(conta);
		return conta;
	}

	public void verificarClienteExistenteEmConta(String cpf) {
		clienteService.obter(cpf);
	}

	public List<Conta> findAllByIdCliente(Long id) {
		List<Conta> contas = contaRepository.findByClienteId(id);
		return contas;
	}

}
