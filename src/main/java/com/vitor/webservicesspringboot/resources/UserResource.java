package com.vitor.webservicesspringboot.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.webservicesspringboot.entities.User;

/*@RestController - Essa anotação indica que a classe é um controlador que lida com requisições web. 
 * Ela combina a funcionalidade de @Controller e @ResponseBody, ou seja, 
 * ele retorna diretamente o objeto e Spring o converte para JSON automaticamente.*/
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@GetMapping
	/*
	 * GetMapping é pra dizer que esse método será chamado em uma requisição GET do
	 * HTTP para /users
	 */
	public ResponseEntity<User> findAll() {
		User u = new User(1L, "Maria", "maria@gmail.com", "12345678", "12345");
		return ResponseEntity.ok().body(u);
	}

}
