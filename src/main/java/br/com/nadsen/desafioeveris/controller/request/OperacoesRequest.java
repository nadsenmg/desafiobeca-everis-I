package br.com.nadsen.desafioeveris.controller.request;

import br.com.nadsen.desafioeveris.model.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacoesRequest {
	
	private int numConta;
	private Double valor;
	private TipoOperacao tipoOperacao;

}
