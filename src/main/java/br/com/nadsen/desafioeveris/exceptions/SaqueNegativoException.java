package br.com.nadsen.desafioeveris.exceptions;

public class SaqueNegativoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SaqueNegativoException(String msg) {
		super(msg);
	}
}
