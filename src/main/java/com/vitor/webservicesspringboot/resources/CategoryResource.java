package com.vitor.webservicesspringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.webservicesspringboot.entities.Category;
import com.vitor.webservicesspringboot.services.CategoryService;

/*@RestController - Essa anotação indica que a classe é um controlador (Controller)
 * que lida com requisições web. 
 * Ela combina a funcionalidade de @Controller e @ResponseBody, ou seja, 
 * ele retorna diretamente o objeto e Spring o converte para JSON automaticamente.*/
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	/*
	 * GetMapping é pra dizer que esse método será chamado em uma requisição GET do
	 * HTTP para /categories
	 * 
	 * ResponseEntity é um tipo específico do spring para retornar respostas para
	 * requisições web. Ele é um Generics, e o tipo da resposta dele está entre <>,
	 * no caso abaixo é uma lista de categorias, então List<Category>
	 */
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	/* Isso indica que dentro da requisição vai ser aceito um id na URL */
	@GetMapping(value = "/{id}")
	/* @PathVariable pro Spring identificar que é o id recebido como parâmetro */
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
