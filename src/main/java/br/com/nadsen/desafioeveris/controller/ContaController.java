package br.com.nadsen.desafioeveris.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.nadsen.desafioeveris.client.SaqueClient;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nadsen.desafioeveris.controller.request.ContaRequest;
import br.com.nadsen.desafioeveris.controller.response.ContaDeleteResponse;
import br.com.nadsen.desafioeveris.controller.response.ContaResponse;
import br.com.nadsen.desafioeveris.controller.response.ExtratoResponse;
import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.Extrato;
import br.com.nadsen.desafioeveris.service.ContaService;
import br.com.nadsen.desafioeveris.service.OperacoesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@Api(value = "API REST CONTAS")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/contas")
public class ContaController {

	private final ContaService contaService;
	private final ModelMapper modelMapper;

	@ApiOperation(value = "Cadastra uma conta")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContaResponse save(@RequestBody @Valid ContaRequest contaRequest) {
		Conta conta = contaService.save(contaRequest);
		return modelMapper.map(conta, ContaResponse.class);
	}

	@ApiOperation(value = "Busca todas as contas")
	@GetMapping
	public List<ContaResponse> findAll(@RequestParam(value = "numConta", required = false) Integer numConta) {
		List<Conta> contas = contaService.findAll(numConta);
		return contas.stream().map(this::toContaResponse).collect(Collectors.toList());
	}

	@ApiOperation(value = "Atualiza uma conta")
	@PutMapping
	public ContaResponse update(@RequestParam @Valid int numConta, @RequestBody ContaRequest contaRequest) {
		Conta conta = contaService.update(numConta, modelMapper.map(contaRequest, Conta.class));
		return modelMapper.map(conta, ContaResponse.class);
	}

	@ApiOperation(value = "Deleta uma conta")
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public ContaDeleteResponse deleteCliente(@RequestParam int numConta) {
		contaService.delete(numConta);
		return new ContaDeleteResponse("Conta deletada com sucesso!");
	}

	private ContaResponse toContaResponse(Conta conta) {
		return modelMapper.map(conta, ContaResponse.class);
	}

}
