package br.com.nadsen.desafioeveris.exceptions;

public class TransferenciaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransferenciaInvalidaException(String msg) {
		super(msg);
	}

}
