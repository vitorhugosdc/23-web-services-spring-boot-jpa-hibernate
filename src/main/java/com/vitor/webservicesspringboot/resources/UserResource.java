package com.vitor.webservicesspringboot.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vitor.webservicesspringboot.entities.User;
import com.vitor.webservicesspringboot.services.UserService;

/*@RestController - Essa anotação indica que a classe é um controlador (Controller)
 * que lida com requisições web. 
 * Ela combina a funcionalidade de @Controller e @ResponseBody, ou seja, 
 * ele retorna diretamente o objeto e Spring o converte para JSON automaticamente.*/
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	/*
	 * GetMapping é pra dizer que esse método será chamado em uma requisição GET do
	 * HTTP para /users
	 * 
	 * ResponseEntity é um tipo específico do spring para retornar respostas para
	 * requisições web. Ele é um Generics, e o tipo da resposta dele está entre <>,
	 * no caso abaixo é uma lista de usuários, então List<User>
	 */
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	/* Isso indica que dentro da requisição vai ser aceito um id na URL */
	@GetMapping(value = "/{id}")
	/* @PathVariable pro Spring identificar que é o id recebido como parâmetro */
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	/* @RequestBody porque o meu endpoint vai receber um OBJETO do tipo User */
	@PostMapping()
	public ResponseEntity<User> insert(@RequestBody User obj) {

		obj = service.insert(obj);

		/*
		 * Por que disso? porque quando é inserido um recurso, é mais adequado retornar
		 * o código de resposta 201, e não 200 padrão, pois o 201 é o código específico
		 * HTTP que significa que foi criado um novo recurso, então, retornamos um
		 * ResponseEntity.created(uri), só que o created espera um objeto do tipo URI,
		 * por quê? Porque o padrão HTTP, quando vamos retornar um 201, é esperado que a
		 * resposta contenha um CABEÇALHO chamado Location, contendo o endereço do novo
		 * recurso INSERIDO
		 * 
		 * o path recebe um padrão para montar a URL, no caso, o recurso inserido vai
		 * ter o caminho /users (padrão) /id novo inserido (/users/id)
		 * 
		 * o método buildAndExpand espera que eu informe o id inserido, no caso, o id do
		 * novo recurso inserido vai estar em obj, por isso obj.getId()
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).body(obj);
	}

	/*
	 * Void porque a resposta da requisição não vai retornar nenhum corpo, será
	 * apenas deletado o usuário e pronto
	 * 
	 * .noContent() retorna resposta vazia com o código de resposta vazia sem
	 * conteúdo 204, HTTP
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
