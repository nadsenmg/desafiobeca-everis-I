package br.com.nadsen.desafioeveris.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "cliente")
@Entity
public class Cliente {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	@Column (nullable = false, unique = true)
	private String cpf;
	@Column (nullable = false)
	private String telefone;
	@Column (nullable = false)
	private String endereco;
	
	@JsonIgnore
	@OneToMany (mappedBy = "cliente", fetch=FetchType.EAGER)
	private List<Conta> contas = new ArrayList<>();
	
	public Cliente(String nome, String cpf, String telefone, String endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
	}

}
