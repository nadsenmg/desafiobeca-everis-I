package br.com.nadsen.desafioeveris.exceptions;

public class ClienteJaExistenteException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ClienteJaExistenteException(String msg) {
		super(msg);
	}
}
