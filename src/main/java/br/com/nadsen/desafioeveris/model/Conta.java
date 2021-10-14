package br.com.nadsen.desafioeveris.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.nadsen.desafioeveris.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false, unique = true)
	private int numConta;
	
	@Enumerated(EnumType.ORDINAL)
	@Column (nullable = false)
	private TipoConta tipoConta;
	
	@Column (nullable = false)
	private int digitoVerificador;
	
	@Column (nullable = false)
	private double saldo;
	
	@JsonSerialize
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "cliente_id")
	private Cliente cliente;

	
}
