package br.com.nadsen.desafioeveris.controller.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaqueResponse {
	
	private Instant horario;
	private Integer numConta;
	private double saldo;
	private Long saquesGratuitos;
	private Double taxa;
}
