package com.vitor.webservicesspringboot.services.exceptions;

/*Exceção personalizada para a nossa camada de serviço, 
 * a nossa camada de serviço tem que ser capaz de lançar exceções DELA, e não exceções diversas*/
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/* ELe vai receber o id do objeto que tentei encontrar e não encontrei */
	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}
