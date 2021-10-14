package br.com.nadsen.desafioeveris.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Saque {
	
	private int numConta;
	private Double valor;
	
}
