package com.vitor.webservicesspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitor.webservicesspringboot.entities.Category;
import com.vitor.webservicesspringboot.repositories.CategoryRepository;

/*Quando um componente vai poder ser injetado, ele precisa ser ser registrado, 
 * no caso como o CategoryService é um Service, registra como @Service 
 * (tem a @Component que é mais genérica, mas usa as especificas)
 * A exceção é os repository, o motivo é explicado no User e Order repository*/
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> findAll() {
		return repository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.get();
	}

}
