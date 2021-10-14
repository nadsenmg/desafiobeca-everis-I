package br.com.nadsen.desafioeveris.exceptions;

public class ClienteObrigatorioException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ClienteObrigatorioException(String msg) {
		super(msg);
	}

}
