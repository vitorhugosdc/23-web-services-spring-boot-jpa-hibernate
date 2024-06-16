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

	public User update(Long id, User obj) {
		/*
		 * o getReferenceById instância um usuário, mas ele NÃO VAI no banco de dados
		 * ainda, ele vai deixar um objeto monitorado. É melhor que usar o findById,
		 * pois o findById vai no banco de dados buscar o objeto usuário. O
		 * getReferenceById ele PREPARA o objeto monitorado para ser mexido e DEPOIS
		 * realizar uma operação no banco de dados, sendo bem melhor
		 */
		User entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(User entity, User obj) {
		/*
		 * Nem todos os dados do usuário serão permitidos serem atualizados, a gente
		 * aqui não permite atualizar Id e nem senha
		 */
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
