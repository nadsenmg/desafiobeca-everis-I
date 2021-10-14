package br.com.nadsen.desafioeveris.controller.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoResponse {

	private Instant horario;

	private ContaResponse conta;
	
	private Long saquesGratuitos;
}
