package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant moment;

	/*
	 * @JsonIgnore pra ao puxar o pedido aparecer o pagamento, mas ao pegar o
	 * pagamento, não aparecer o pedido Payment é a classe dependente
	 * 
	 * 
	 * A gente coloca o @MapsId na classe dependente (@OneToOne vai nas duas) para
	 * indicar que o Id do Payment vai ser igual a do Order, no caso pagamento, pois
	 * um pedido por ter 0 (zero) ou 1 pagamento, então o pedido claramente é a
	 * classe dependente
	 */
	@JsonIgnore
	@OneToOne
	@MapsId
	private Order order;

	public Payment() {
	}

	public Payment(Long id, Instant moment, Order order) {
		this.id = id;
		this.moment = moment;
		this.order = order;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, moment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id) && Objects.equals(moment, other.moment);
	}
}
