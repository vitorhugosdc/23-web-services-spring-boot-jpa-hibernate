package com.vitor.webservicesspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.webservicesspringboot.entities.User;

/*Só isso aqui já é o suficiente pra instanciar um objeto repository com várias operações para trabalhar com o Usuário por padrão
 * Não é necessário implementar essa interface, porque o Spring Data JPA já possui uma implementação para essa interface*/
/*User é a minha entidade e Long é o tipo do id da entidade*/
/*Como o UserRepository herda do JpaRepository, 
 * não precisa colocar @Repository pra registrar ele, pois ele já é registrado na classe pai*/
public interface UserRepository extends JpaRepository<User, Long> {

}
