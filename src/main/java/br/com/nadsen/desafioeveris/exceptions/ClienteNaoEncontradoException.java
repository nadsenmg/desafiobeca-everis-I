package br.com.nadsen.desafioeveris.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ClienteNaoEncontradoException(String msg) {
		super("Cliente não encontrado em nosso sistema! ");
	}
}
