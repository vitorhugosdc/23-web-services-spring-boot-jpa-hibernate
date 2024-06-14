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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;

	/*
	 * Por que do Set ao invés de List? Como o Set representa um conjunto, eu não
	 * vou poder ter o mesmo produto com mais de uma categoria IGUAL, o Set por
	 * padrão já impede repetições, então um mesmo produto não pode ter a mesma
	 * categoria mais de uma vez. Também já instanciamos com o new para garantir que
	 * o Set comece vazio e não nullo.
	 */
	/*
	 * Quando temos associação MUITOS-PARA-MUITOS (N..N), colocamos a
	 * annotation @ManyToMany e, em ALGUMA DAS entidades que possuem o MUITOS (N)
	 * colocamos a anotação abaixo dele, o @JoinTable, o que ele faz?.
	 * 
	 * @JoinTable cria uma tabela extra, a chamada tabela de associações, que é como
	 * solucionamos em banco de dados problemas de muitos-para-muitos (N..N),
	 * portanto, é criado uma tabela a mais que armazena somente as chaves
	 * product_id e category_id.
	 * 
	 * @JoinTable(name = "tb_product_category",... define o nome dessa tabela extra
	 * como "tb_product_category".
	 * 
	 * joinColumns = @JoinColumn(name = "product_id")... define o nome da chave
	 * estrangeira referente a tabela de produtos (produtos vem primeiro pois
	 * estamos fazendo isso na classe Product, não Category) lá na tabela de
	 * associação.
	 * 
	 * inverseJoinColumns = @JoinColumn(name = "category_id") define o nome da chave
	 * estrangeira da outra entidade lá na tabela de associação. Como estou na
	 * entidade Product, a "outra entidade" é a Category.
	 * 
	 * A tabela de associação armazena as chaves estrangeiras das duas tabelas
	 * associadas, Product e Category
	 */
	/*
	 * A entidade que define a @JoinTable é aquela que tem o controle sobre a
	 * definição das colunas da tabela de junção, ou seja, ela é a "dona" da relação
	 * 
	 * A manipulação dos dados é feita do ponto de vista da entidade que define a
	 * relação. Por exemplo, se a @JoinTable estiver na entidade Product, a gente
	 * normalmente vai adicionar ou remover categorias a partir de um produto. Se
	 * estiver na entidade Category, você adicionaria ou removeria produtos a partir
	 * de uma categoria.
	 */
	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	/* Igual da classe Order, porém, com id.product */
	@OneToMany(mappedBy = "id.product")
	private Set<OrderItem> items = new HashSet<>();

	public Product() {
	}

	public Product(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	/*
	 * Diferente do pedido (Order) onde faz sentido eu possa acessar os itens do
	 * pedido (OrderItem) ao acessar o pedido, pra ter todos os dados do pedido na
	 * relação, aqui, não faz muito sentido eu ter um Produto (Product) e acessar
	 * todos os itens de pedido dele (que é o caso da classe Order), faz muito mais
	 * sentido eu acessar um produto e, acessar todos os pedidos no qual ele está,
	 * portanto, a gente percorre todos os items de pedido desse produto e, acessa
	 * os PEDIDOS dele, adicionando ao Set.
	 * 
	 * Resumindo, a gente não quer os items de pedido em sí, a gente quer o pedido
	 * desse item de pedido
	 */
	/*
	 * Eu não quero que ao buscar um produto apareça os pedidos dele, eu quero
	 * buscar um pedido e quero que apareça os itens do pedido e, para cada item de
	 * pedido, o produto pendurado nele. Portanto o @JsonIgnore vai na classe
	 * Product dessa vez ao invés de OrderItem
	 */
	@JsonIgnore
	public Set<Order> getOrders() {
		Set<Order> set = new HashSet<>();
		for (OrderItem x : items) {
			set.add(x.getOrder());
		}
		return set;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
}
