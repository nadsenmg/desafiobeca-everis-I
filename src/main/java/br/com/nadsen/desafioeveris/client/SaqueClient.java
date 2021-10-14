package br.com.nadsen.desafioeveris.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient (name = "saqueClient", url = "http://localhost:8081/conta")
public interface SaqueClient {

	@GetMapping ("/{numConta}/qtdSaques")
	public SaquesClientResponse getTotalSaques(@PathVariable Integer numConta);
}
