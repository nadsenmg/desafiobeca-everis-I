package br.com.nadsen.desafioeveris.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClienteContaExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		String error = "Essa conta já foi registrada em nosso sistema!";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(error);
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ClienteJaExistenteException.class)
	public ResponseEntity<StandardError> handleClienTeExistenteException(ClienteJaExistenteException e) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String message = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" / "));
		StandardErrorMethodArgument st = new StandardErrorMethodArgument(List.of(fields), message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(st);
	}
	
	@ExceptionHandler (ClienteNaoEncontradoException.class)
	public ResponseEntity<StandardError> handleClienteNaoEncontradoException (ClienteNaoEncontradoException e){
		StandardError errors = new StandardError(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}


	@ExceptionHandler(ContaJaExistenteException.class)
	public ResponseEntity<StandardError> handleContaExistenteException(ContaJaExistenteException e) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(e.getMessage());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ClienteObrigatorioException.class)
	public ResponseEntity<StandardError> handleClienteObrigatorioException(ClienteObrigatorioException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	
}