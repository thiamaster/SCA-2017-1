package br.cefetrj.sca.infra.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DisciplinaWS {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("codigo")  
	private String codigo;
	
	@JsonProperty("nome")
	private String nome;

	@JsonCreator
	public DisciplinaWS(Long id, String codigo, String nome) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("codigo")  
	public String getCodigo() {
		return codigo;
	}

	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}
	
}
