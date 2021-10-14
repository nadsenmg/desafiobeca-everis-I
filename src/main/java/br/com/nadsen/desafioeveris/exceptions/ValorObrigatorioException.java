package br.com.nadsen.desafioeveris.exceptions;

public class ValorObrigatorioException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ValorObrigatorioException(String msg) {
		super(msg);
	}
}
