package br.com.nadsen.desafioeveris.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia {
	
	private int numContaOrigem;
	private int numContaDestino;
	private Double valor;
}
