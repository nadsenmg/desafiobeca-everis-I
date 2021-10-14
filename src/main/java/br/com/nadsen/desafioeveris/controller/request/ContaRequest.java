package br.com.nadsen.desafioeveris.controller.request;

import br.com.nadsen.desafioeveris.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequest {
	
	private Integer numConta;
	
	private TipoConta tipoConta;
	
	private Integer digitoVerificador;

	private Double saldo;
	
	private String clienteCpf;
	
}

