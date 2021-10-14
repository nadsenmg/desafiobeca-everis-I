package br.com.nadsen.desafioeveris.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class ClienteRequest {
	
	private Long id;
	
	@NotBlank(message = "O nome deve ser preenchido")
	private String nome;

	@CPF @Size(min = 11, message = "O CPF deve ter 11 digitos")
	@NotBlank(message = "CPF deve ser válido")
	private String cpf;

	@NotBlank(message = "Telefone deve ser preenchido")
	@Pattern(regexp= "(\\(\\d{2}\\)\\s)(\\d{4,5}\\-\\d{4})", 
	message = "Formato para telefones celulares devem ser (DD) 9XXX-XXXX ou (DD) XXXX-XXXX para telefones fixos ")
	private String telefone;

	@NotBlank(message = "Endereço deve ser preenchido")
	@Size(min = 2, max = 100, message = "Tamanho mínimo de 2 caracteres e máximo de 100")
	private String endereco;

}