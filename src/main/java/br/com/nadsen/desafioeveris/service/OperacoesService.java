package br.com.nadsen.desafioeveris.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nadsen.desafioeveris.client.SaqueClient;
import br.com.nadsen.desafioeveris.client.SaquesClientResponse;
import br.com.nadsen.desafioeveris.evento.Evento;
import br.com.nadsen.desafioeveris.exceptions.SaldoInsuficienteException;
import br.com.nadsen.desafioeveris.exceptions.SaqueNegativoException;
import br.com.nadsen.desafioeveris.exceptions.TransferenciaInvalidaException;
import br.com.nadsen.desafioeveris.exceptions.ValorObrigatorioException;
import br.com.nadsen.desafioeveris.model.ConfiguracaoTipoConta;
import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.Deposito;
import br.com.nadsen.desafioeveris.model.Extrato;
import br.com.nadsen.desafioeveris.model.Operacao;
import br.com.nadsen.desafioeveris.model.Saque;
import br.com.nadsen.desafioeveris.model.Transferencia;
import br.com.nadsen.desafioeveris.model.enums.TipoEvento;
import br.com.nadsen.desafioeveris.model.enums.TipoOperacao;
import br.com.nadsen.desafioeveris.repository.ContaRepository;
import br.com.nadsen.desafioeveris.repository.OperacoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperacoesService {

	private final ContaRepository contaRepository;
	private final OperacoesRepository operacoesRepository;
	private final TipoContaService tipoContaService;
	private final ModelMapper modelMapper;
	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final SaqueClient saqueClient;

	@Transactional
	public void sacar(Saque saque) {
		Conta conta = Optional.ofNullable(contaRepository.getByNumConta(saque.getNumConta()))
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
		double taxa = getTaxa(conta, getTotalSaques(saque.getNumConta()));
		validarValorNegativo(saque.getValor());
		validarSaldo(saque.getValor(), taxa, conta);
		conta.setSaldo(conta.getSaldo() - saque.getValor() - taxa);
		validarTaxaParaSaqueExcedido(conta);
		try {
			Evento evento = modelMapper.map(conta, Evento.class);
			evento.setTipoEvento(TipoEvento.SAQUE_EFETUADO);
			 kafkaTemplate.send("CONTROLE_SAQUES", String.valueOf(saque.getNumConta()), objectMapper.writeValueAsString
	                    (new Evento(evento.getTipoEvento(), evento.getNumConta(), evento.getTipoConta(),
								LocalDateTime.now().toString())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		log.info("SAQUE EFETUADO COM SUCCESSO! -> " + saque);
		contaRepository.save(conta);
		operacoesRepository.save(new Operacao(null, conta.getNumConta(), saque.getValor(), taxa, TipoOperacao.SAQUE));
	}

	public double getTaxa(Conta conta, long qtdSaque) {
		ConfiguracaoTipoConta configuracaoTipoConta = tipoContaService.getConfiguracaoTipoConta(conta.getTipoConta());
		double taxa = 0D;
		if (qtdSaque <= configuracaoTipoConta.getQtdSaque()) {
			taxa = configuracaoTipoConta.getTaxa();
		}
		return taxa;
	}

	@Transactional
	public void depositar(Deposito deposito) {
		Conta conta = contaRepository.getByNumConta(deposito.getNumConta());
		validarValorNegativo(deposito.getValor());
		conta.setSaldo(conta.getSaldo() + deposito.getValor());
		contaRepository.save(conta);
		operacoesRepository
				.save(new Operacao(null, conta.getNumConta(), deposito.getValor(), 0D, TipoOperacao.DEPOSITO));
	}

	@Transactional
	public void transferir(Transferencia transferencia) {
		Conta contaOrigem = Optional.ofNullable(contaRepository.getByNumConta(transferencia.getNumContaOrigem()))
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada!"));
		Conta contaDestino = Optional.ofNullable(contaRepository.getByNumConta(transferencia.getNumContaDestino()))
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada!"));
		validarContasDistintas(contaOrigem, contaDestino);
		validarSaldo(transferencia.getValor(), 0, contaOrigem);
		validarValorNegativo(transferencia.getValor());
		contaOrigem.setSaldo(contaOrigem.getSaldo() - transferencia.getValor());
		contaDestino.setSaldo(contaDestino.getSaldo() + transferencia.getValor());

		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);
		operacoesRepository.save(new Operacao(null, transferencia.getNumContaOrigem(), transferencia.getValor(), 0D,
				TipoOperacao.TRANSFERENCIA_SAIDA));
		operacoesRepository.save(new Operacao(null, transferencia.getNumContaDestino(), transferencia.getValor(), 0D,
				TipoOperacao.TRANFERENCIA_ENTRADA));
	}

	public Double saldo(Integer numConta) {
		Conta conta = contaRepository.getByNumConta(numConta);
		return conta.getSaldo();
	}

	public Extrato getExtrato(Integer numConta) {
		Long saqueDisponiveis = getTotalSaques(numConta);
		return new Extrato(Instant.now(), contaRepository.getByNumConta(numConta), saqueDisponiveis);
	}

	private void validarContasDistintas(Conta contaOrigem, Conta contaDestino) {
		if (contaOrigem.getNumConta() == contaDestino.getNumConta()) {
			throw new TransferenciaInvalidaException("Conta de origem e destino devem ser diferentes");
		}
	}

	private void validarSaldo(double valor, double taxa, Conta conta) {
		if (conta.getSaldo() < valor + taxa) {
			throw new SaldoInsuficienteException();
		}
	}

	public Double validarTaxaParaSaqueExcedido(Conta conta) {
		double taxa = 0D;
		if (getTotalSaques(conta.getNumConta()) > 0) {
			return taxa;
		}
		taxa = conta.getTipoConta().getTaxa();
		return taxa;
	}

	private void validarValorNegativo(double valor) {
		if (valor < 0) {
			throw new SaqueNegativoException("Não é permitido realizar está operação para valores negativos");
		} else if (valor == 0) {
			throw new ValorObrigatorioException("Obrigatorio informar um valor acima de 0 para esta operação");
		}
	}

	public long getTotalSaques(Integer numConta){
		 SaquesClientResponse saquesClientResponse = saqueClient.getTotalSaques(numConta);
		return saquesClientResponse.getSaquesGratuitos();
	}

}