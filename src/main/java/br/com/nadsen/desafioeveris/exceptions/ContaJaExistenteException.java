package br.com.nadsen.desafioeveris.exceptions;

public class ContaJaExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContaJaExistenteException(String msg) {
		super(msg);
	}
}
