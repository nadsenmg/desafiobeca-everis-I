package br.com.nadsen.desafioeveris.evento;

import br.com.nadsen.desafioeveris.model.enums.TipoConta;
import br.com.nadsen.desafioeveris.model.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Evento {

	private TipoEvento tipoEvento;
	private Integer numConta;
	private TipoConta tipoConta;
	private String dataEvento;
}
