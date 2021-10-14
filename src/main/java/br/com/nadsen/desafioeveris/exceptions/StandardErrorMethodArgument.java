package br.com.nadsen.desafioeveris.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardErrorMethodArgument {

	private List<String> campos;
	private String message;
}
