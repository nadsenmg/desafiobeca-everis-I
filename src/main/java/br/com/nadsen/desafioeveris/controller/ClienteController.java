package br.com.nadsen.desafioeveris.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nadsen.desafioeveris.controller.request.ClienteRequest;
import br.com.nadsen.desafioeveris.controller.response.ClienteDeleteResponse;
import br.com.nadsen.desafioeveris.controller.response.ClienteResponse;
import br.com.nadsen.desafioeveris.controller.response.ContaResponse;
import br.com.nadsen.desafioeveris.model.Cliente;
import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.service.ClienteService;
import br.com.nadsen.desafioeveris.service.ContaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "API REST CLIENTES")
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
@RequiredArgsConstructor
@RestController
public class ClienteController {

	private final ContaService contaService;
	private final ClienteService clienteService;
	private final ModelMapper modelMapper;

	@ApiOperation(value = "Cadastra um cliente")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ClienteResponse save(@RequestBody @Valid ClienteRequest clienteRequest) {
		Cliente cliente = this.clienteService.save(modelMapper.map(clienteRequest, Cliente.class));
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	@ApiOperation(value = "Busca todos clientes")
	@GetMapping
	public List<ClienteResponse> findAll(@RequestParam(required = false) String cpf) {
		List<Cliente> clientes = clienteService.findAll(cpf);
		return clientes.stream().map(this::toClienteResponse).collect(Collectors.toList());
	}

	@ApiOperation(value = "Busca todas contas de clientes")
	@GetMapping("/{id}/contas")
	public List<ContaResponse> findClientesContas(@PathVariable Long id) {
		List<Conta> contas = contaService.findAllByIdCliente(id);
		return contas.stream().map(this::toContaResponse).collect(Collectors.toList());
	}

	@ApiOperation(value = "Atualiza um cliente")
	@PutMapping()
	public ClienteResponse update(@RequestParam String cpf, @RequestBody ClienteRequest clienteRequest) {
		Cliente cliente = clienteService.update(cpf, modelMapper.map(clienteRequest, Cliente.class));
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	@ApiOperation(value = "Deleta um cliente")
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping()
	public ClienteDeleteResponse deleteCliente(@RequestParam String cpf) {
		clienteService.delete(cpf);
		return new ClienteDeleteResponse("Cliente deletado com sucesso!");
	}

	private ClienteResponse toClienteResponse(Cliente cliente) {
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	private ContaResponse toContaResponse(Conta conta) {
		return modelMapper.map(conta, ContaResponse.class);
	}

}