package br.com.nadsen.desafioeveris.controller;

import java.time.Instant;

import br.com.nadsen.desafioeveris.client.SaqueClient;
import br.com.nadsen.desafioeveris.client.SaquesClientResponse;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nadsen.desafioeveris.controller.request.SaqueRequest;
import br.com.nadsen.desafioeveris.controller.request.TransferenciaRequest;
import br.com.nadsen.desafioeveris.controller.response.ExtratoResponse;
import br.com.nadsen.desafioeveris.controller.response.SaldoResponse;
import br.com.nadsen.desafioeveris.controller.response.SaqueResponse;
import br.com.nadsen.desafioeveris.controller.response.TransferenciaResponse;
import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.Extrato;
import br.com.nadsen.desafioeveris.model.Saque;
import br.com.nadsen.desafioeveris.model.Transferencia;
import br.com.nadsen.desafioeveris.service.ContaService;
import br.com.nadsen.desafioeveris.service.OperacoesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/operacoes")
@RequiredArgsConstructor
public class OperacoesController {

	private final OperacoesService operacoesService;
	private final ContaService contaService;
	private final ModelMapper modelMapper;
	private final SaqueClient saqueClient;
	
	@ApiOperation(value = "Operação de extrato")
	@GetMapping("/extrato")
	public ExtratoResponse extrato(@RequestParam Integer numConta) {
		Extrato extrato = operacoesService.getExtrato(numConta);
		return modelMapper.map(extrato, ExtratoResponse.class);
	}

	@ApiOperation(value = "Operação de saldo")
	@GetMapping("/saldo")
	public SaldoResponse saldo(@RequestParam Integer numConta) {
		Conta conta = contaService.findByNumConta(numConta);
		return new SaldoResponse(Instant.now(), conta.getSaldo());
	}

	@ApiOperation(value = "Operação de saque")
	@PostMapping("/saque")
	public SaqueResponse saque(@RequestBody SaqueRequest saqueRequest) {
		Conta conta = contaService.findByNumConta(saqueRequest.getNumConta());
		operacoesService.sacar(modelMapper.map(saqueRequest, Saque.class));
		Double taxa = operacoesService.validarTaxaParaSaqueExcedido(conta);
		SaquesClientResponse saques = saqueClient.getTotalSaques(saqueRequest.getNumConta());
		SaqueResponse saqueResponse = modelMapper.map(saques, SaqueResponse.class);
		return new SaqueResponse(Instant.now(), saqueRequest.getNumConta(),
				contaService.getSaldo(saqueRequest.getNumConta()), saqueResponse.getSaquesGratuitos(), taxa);
	}

	@ApiOperation(value = "Operação de transferencia")
	@PutMapping("/transferencia")
	public TransferenciaResponse transferencia(@RequestBody TransferenciaRequest transferenciaRequest) {
		operacoesService.transferir(modelMapper.map(transferenciaRequest, Transferencia.class));
		return new TransferenciaResponse(Instant.now(), "Transferencia efetuada com sucesso",
				transferenciaRequest.getValor(), contaService.getSaldo(transferenciaRequest.getNumContaOrigem()));
	}
}