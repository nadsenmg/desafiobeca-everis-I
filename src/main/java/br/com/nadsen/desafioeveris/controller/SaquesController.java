package br.com.nadsen.desafioeveris.controller;

import br.com.nadsen.desafioeveris.client.SaqueClient;
import br.com.nadsen.desafioeveris.client.SaquesClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/total-saques")
public class SaquesController {

    private final SaqueClient saqueClient;

    @GetMapping("{numConta}")
    public SaquesClientResponse getSaques(@PathVariable("numConta") String numConta) {
        return saqueClient.getTotalSaques(Integer.valueOf(numConta));
    }
}
