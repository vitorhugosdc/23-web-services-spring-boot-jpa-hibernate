package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	/* Uma mesma categoria não pode ter mais de uma vez um mesmo produto */
	/*
	 * Aqui é similar ao @OneToMany da classe User, em mappedBy = "categories" -
	 * "categories" é o nome EXATO da coleção lá na outra classe, ou seja, a classe
	 * Product cujo nome da coleção de categorias é categories.
	 * 
	 * Lembrando que toda a associação de muitos-para-muitos e da tabela de
	 * associação foi feita na classe Product, simplesmente por preferência, poderia
	 * ser aqui ao invés de lá, assim, lá teria esse mappedBy.
	 * 
	 * Lembrando também de não esquecer do @JsonIgnore, pois como é uma associação
	 * bi-direcional, novamente se eu não colocar nada, ao chamar os produtos ou
	 * categories vai entrar em looping infinito, portanto, coloca em uma das
	 * entidades essa anotação. No caso, foi colocado aqui em Category. Ao colocar
	 * em Category, ao chamar os produtos vai aparecer também as categories dele,
	 * mas ao chamar as categorias, não irá aparecer os produtos que ela tem, pois
	 * o @JsonIgnore está aqui
	 */
	@JsonIgnore
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>();

	public Category() {
	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
}
