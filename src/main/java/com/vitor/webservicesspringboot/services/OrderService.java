package com.vitor.webservicesspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitor.webservicesspringboot.entities.Order;
import com.vitor.webservicesspringboot.repositories.OrderRepository;

/*Quando um componente vai poder ser injetado, ele precisa ser ser registrado, 
 * no caso como o OrderService é um Service, registra como @Service 
 * (tem a @Component que é mais genérica, mas usa as especificas)*/
@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}

}
