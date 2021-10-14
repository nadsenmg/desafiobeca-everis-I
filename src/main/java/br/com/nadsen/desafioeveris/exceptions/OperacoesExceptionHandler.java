package br.com.nadsen.desafioeveris.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OperacoesExceptionHandler {

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<StandardError> handleSaldoInsuficienteException(SaldoInsuficienteException e) {
		String error = "Saldo insuficiente para essa operação";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(TransferenciaInvalidaException.class)
	public ResponseEntity<StandardError> handleTransferenciaInvalidaException(TransferenciaInvalidaException e) {
		String error = e.getMessage();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ContaNaoEncontradaException.class)
	public ResponseEntity<StandardError> handleContaNaoEncontradaExceptioon(ContaNaoEncontradaException e) {
		String error = e.getMessage();
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(SaqueNegativoException.class)
	public ResponseEntity<StandardError> handleSaqueNegativoException(SaqueNegativoException e) {
		String error = e.getMessage();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ValorObrigatorioException.class)
	public ResponseEntity<StandardError> handleValorObrigatorioException(ValorObrigatorioException e) {
		String error = e.getMessage();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> handleException(Exception e) {
		String error = e.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}

	
}
