package br.com.nadsen.desafioeveris.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Extrato {
	
	private Instant horario;
	
	private Conta conta;
	
	private Long qtdSaquesDisponiveis;
	
}
