package br.com.nadsen.desafioeveris.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracaoTipoConta {
	
	private double taxa;
	private int qtdSaque;
}
