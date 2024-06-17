package com.vitor.webservicesspringboot.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vitor.webservicesspringboot.services.exceptions.DatabaseException;
import com.vitor.webservicesspringboot.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

/*É nessa classe que iremos dar o tratamento manual para nossos erros da camada resource*/
/*@ControllerAdvice é para interceptar as exceções que acontecerem, para que esse objeto possa executar um possível tratamento*/
@ControllerAdvice
public class ResourceExceptionHandler {

	/*
	 * @ExceptionHandler(ResourceNotFoundException.class) é para anotar que esse
	 * método abaixo vai interceptar qualquer exceção desse tipo
	 * ResourceNotFoundException que for lançada, ou seja, quando o serviço
	 * UserService lançar essa exceção, será capturada aqui
	 * 
	 * Basicamente: o UserService por exemplo vai lançar uma
	 * ResourceNotFoundException, como no método findById por exemplo, ai, o método
	 * resourceNotFound abaixo vai interceptar qualquer exceção daquela classe,
	 * então vai cair nele, ai, a gente instância um erro padrão, só que manualmente
	 * e com nossos dados mais detalhados, tudo é instanciado no resourceNotFound
	 * abaixo, só a mensagem do atributo mensagem da classe StandardError que vai
	 * ser obtida através da ResourceNotFoundException original
	 */
	/*
	 * Retorno é um ResponseEntity com o objeto da classe Standard Error que criamos
	 * 
	 * Ele recebe uma exceção do nosso tipo personalizado ResourceNotFoundException
	 * e um HttpServletRequest, que representa a solicitação HTTP que foi feita ao
	 * servidor
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/* Intercepta qualquer exceção do tipo DatabaseException */
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
