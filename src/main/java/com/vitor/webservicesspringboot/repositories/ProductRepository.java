package com.vitor.webservicesspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.webservicesspringboot.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
