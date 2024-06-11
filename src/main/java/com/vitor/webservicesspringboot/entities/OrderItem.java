package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.util.Objects;

import com.vitor.webservicesspringboot.entities.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Por não ser um Id Long como as outras classes e por ser composto, a gente
	 * coloca a annotation @EmbeddedId (a classe OrderItemPK tem que ter a
	 * annotation @Embeddable)
	 */
	@EmbeddedId
	private OrderItemPK id;
	private Integer quantity;
	/*
	 * Por que repetir o price no Product e na OrderItem? Para manter histórico,
	 * caso o preço do produto mude, eu vou ter quanto esse produto custou na época
	 * do pedido através da OrderItem
	 */
	private Double price;

	public OrderItem() {
	}

	/* O construtor também recebe um Order e um Product */
	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		/*
		 * A gente seta o Order e Product nesse OrderItem com os sets da classe auxiliar
		 * criada
		 */
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

	/*
	 * Criamos os getters e setters manualmente para o Order e Product associados a
	 * esse OrderItem, para não retornar o objeto composto OrderItemPK, retornaremos
	 * cada um deles certinho
	 */
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}

	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}

}
