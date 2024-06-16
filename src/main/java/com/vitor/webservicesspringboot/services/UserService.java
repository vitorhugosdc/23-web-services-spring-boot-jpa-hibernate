package com.vitor.webservicesspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitor.webservicesspringboot.entities.User;
import com.vitor.webservicesspringboot.repositories.UserRepository;

/*Quando um componente vai poder ser injetado, ele precisa ser ser registrado, 
 * no caso como o UserService é um Service, registra como @Service 
 * (tem a @Component que é mais genérica, mas usa as especificas)
 * A exceção é os repository, o motivo é explicado no User e Order repository*/
@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}

	public User insert(User obj) {
		/* Por padrão o .save do repository já retorna o usuário salvo */
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
