package br.com.nadsen.desafioeveris.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.Extrato;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		
		
		modelMapper.createTypeMap(Conta.class, Extrato.class).addMapping(src -> src.getCliente().getId(),
				(dest, value) -> dest.setConta((Conta) value));

		return modelMapper;
	}
}
