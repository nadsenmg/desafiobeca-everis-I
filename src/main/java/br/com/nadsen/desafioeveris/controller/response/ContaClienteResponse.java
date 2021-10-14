package br.com.nadsen.desafioeveris.controller.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaClienteResponse {

	public ClienteResponse cliente;
	public List<ContaResponse> contas;
}
