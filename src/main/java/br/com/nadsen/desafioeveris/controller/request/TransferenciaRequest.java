package br.com.nadsen.desafioeveris.controller.request;

import br.com.nadsen.desafioeveris.model.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaRequest {
	
	private Integer numContaOrigem;
	private Integer numContaDestino;
	private Double valor;
	
	public Transferencia toTransferencia (TransferenciaRequest request) {
		Transferencia transferencia = new Transferencia(request.numContaOrigem, request.numContaDestino, request.getValor());
		return transferencia;
	}
	
	public TransferenciaRequest toTransferenciaRequest (Transferencia transferencia) {
		TransferenciaRequest transferenciaRequest = new TransferenciaRequest(transferencia.getNumContaOrigem(), transferencia.getNumContaDestino(), transferencia.getValor());
		return transferenciaRequest;
	}
}

