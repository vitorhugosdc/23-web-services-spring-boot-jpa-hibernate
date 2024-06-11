package com.vitor.webservicesspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.webservicesspringboot.entities.Order;

/*Só isso aqui já é o suficiente pra instanciar um objeto repository com várias operações para trabalhar com o Order por padrão
 * Não é necessário implementar essa interface, porque o Spring Data JPA já possui uma implementação para essa interface*/
/*Order é a minha entidade e Long é o tipo do id da entidade*/
/*Como o OrderRepository herda do JpaRepository, 
 * não precisa colocar @Repository pra registrar ele, pois ele já é registrado na classe pai*/
public interface OrderRepository extends JpaRepository<Order, Long> {

}
