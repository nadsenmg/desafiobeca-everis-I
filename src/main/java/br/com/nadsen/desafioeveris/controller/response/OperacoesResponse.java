package br.com.nadsen.desafioeveris.controller.response;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.service.OperacoesService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacoesResponse {

	@Autowired
	private OperacoesService operacoesService;

	private String message;
	private double valor;
	private double saldo;
	private Conta conta;


}
