package br.com.nadsen.desafioeveris.controller.response;

import br.com.nadsen.desafioeveris.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponse {
	
	private Long id;
	
	private Integer numConta;

	private TipoConta tipoConta;

	private Integer digitoVerificador;

	private Double saldo;	
	
	
}
