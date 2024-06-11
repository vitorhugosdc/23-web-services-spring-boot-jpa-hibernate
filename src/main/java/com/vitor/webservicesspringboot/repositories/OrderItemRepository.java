package com.vitor.webservicesspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.webservicesspringboot.entities.OrderItem;
import com.vitor.webservicesspringboot.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
