package br.com.nadsen.desafioeveris.controller.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponse {

	private Instant horario;
	private String message;
	private Double valor;
	private Double saldo;
	
}
