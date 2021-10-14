package br.com.nadsen.desafioeveris.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaqueRequest {
	
	private int numConta;
	private Double valor;
}
