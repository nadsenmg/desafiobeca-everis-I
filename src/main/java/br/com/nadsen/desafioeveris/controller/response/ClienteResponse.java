package br.com.nadsen.desafioeveris.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
	
	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private String endereco;
	
}
