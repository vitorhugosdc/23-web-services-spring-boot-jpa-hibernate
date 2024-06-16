package com.vitor.webservicesspringboot.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Esses atributos são retornados por padrão do Spring em um objeto de erro,
	 * como a gente quer fazer um tratamento manual das exceções e retornar um
	 * objeto similar ao que o Spring retorna, criamos essa classe
	 * 
	 * Por que no pacote Resources? porque o objeto retornado pelo Spring de erro é
	 * um objeto que já está na resposta da requisição, e quem trabalha com
	 * requisição e mexe com isso é a camada Resource (Controller)
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardError() {
	}

	public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * Não precisa do hashCode e Equals pois em momento algum nos interessa comparar
	 * um erro com outro
	 */
}
