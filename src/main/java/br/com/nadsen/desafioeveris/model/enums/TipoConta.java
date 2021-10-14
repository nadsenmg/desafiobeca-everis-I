package br.com.nadsen.desafioeveris.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoConta {
	PESSOA_FISICA(10), PESSOA_JURIDICA(10), GOVERNAMENTAL(20);
	
	private double taxa;
	
	
	
}
