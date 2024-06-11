package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitor.webservicesspringboot.entities.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order")
/*
 * A palavra Order é uma palavra reservada no banco de dados H2, por isso foi
 * posto tb_order
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * Garantir que lá no Json, o instante do pedido seja mostrado no formato de
	 * String do ISO 8601 Instant.parse("2019-06-20T19:53:07Z")
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;

	/*
	 * Ao invés de ser 'private OrderStatus orderStatus', internamente na classe
	 * Order iremos deixar EXPLÍCITO que estamos gravando no banco de dados um
	 * número inteiro, porém, esse tratamento de número inteiro é só internamente na
	 * classe Order. Para o mundo externo, ainda estarei mantendo o OrderStatus
	 */
	private Integer orderStatus;

	/* Muitos pedidos par 1 cliente */
	@ManyToOne
	/*
	 * client_id vai ser o nome da chave estrangeira na tabela Order que armazena o
	 * id do Usuário que esta associado a esse pedido
	 */
	@JoinColumn(name = "client_id")
	private User client;

	public Order() {
	}

	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		this.id = id;
		this.moment = moment;
		/*
		 * Utilizando o setOrderStatus para converter o orderStatus para seu respectivo
		 * Integer
		 */
		setOrderStatus(orderStatus);
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public OrderStatus getOrderStatus() {
		/*
		 * Aqui está sendo utilizado o método que criamos para converter o inteiro
		 * interno aqui da classe para OrderStatus
		 */
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		/*
		 * Fazendo o inverso do getOrder, aqui estamos convertendo um OrderStatus para
		 * um inteiro utilizando a função getCode que implementamos
		 */
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

}
