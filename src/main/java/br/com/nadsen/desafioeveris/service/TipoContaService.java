package br.com.nadsen.desafioeveris.service;

import org.springframework.stereotype.Service;

import br.com.nadsen.desafioeveris.model.ConfiguracaoTipoConta;
import br.com.nadsen.desafioeveris.model.enums.TipoConta;

@Service
public class TipoContaService {

	public ConfiguracaoTipoConta getConfiguracaoTipoConta(TipoConta tipoConta) {
		if (tipoConta == TipoConta.PESSOA_FISICA) {
			return new ConfiguracaoTipoConta(10, 5);
		} else if (tipoConta == TipoConta.PESSOA_JURIDICA) {
			return new ConfiguracaoTipoConta(10, 50);
		} else {
			return new ConfiguracaoTipoConta(25, 250);
		}
	}
}
