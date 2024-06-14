package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*Sempre que for criar uma entidade em Java (Spring Boot pelo menos) tem que criar nesta exata ordem

Basic entity checklist:

- Basic attributes

- Associations (instantiate collections)

- Constructors

- Getters & Setters (collections: only get)

- hashCode & equals

- Serializable*/

/*Definindo que User é uma entidade e, assim, uma tabela no banco de dados*/
@Entity
/*
 * Aqui a gente define o nome da tabela dessa entidade User, se não tiver a
 * linha abaixo o JPA usa o nome da própria classe, User, porém, a palavra User
 * é uma palavra reservada no banco de dados H2, por isso foi posto tb_user
 */
@Table(name = "tb_user")
/*
 * Por que do Implements Serializable?
 * 
 * É pros objetos poderem serem transformados em sequência de bytes, para gravar
 * em arquivo, trafegar em rede, etc
 */
/*
 * @Entity pra identificar que essa é uma entidade de domínio que vai
 * corresponder a uma tabela
 * 
 * Por padrão, O JPA cria uma tabela com o mesmo nome da classe e, colunas com
 * os mesmos nomes dos atributos
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Informando ao JPA qual a chave primária da tabela no banco de dados */
	@Id
	/* Essa daqui é pra dizer que ela é autoincrementável no banco de dados */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String password;

	/*
	 * @JsonIgnore na associação de pedidos do cliente, para não haver loopings:
	 * Cuidado, uma associação de mão dupla, como por exemplo Usuário e Pedido, onde
	 * um usuário tem pedidos e pedidos tem usuários pode gerar um looping infinito
	 * na requição HTTP, pois retorna o Json do usuário, ai os pedidos deles, ai os
	 * pedidos tem usuários, e os usuários pedidos, etc. Pra evitar isso,
	 * coloca @JsonIgnore em pelo menos uma das classes associadas, como por
	 * exemplo, em cima da lista de pedidos do cliente:
	 * 
	 * Por padrão, quando se tem uma associação MUITOS-PARA-UM, se eu carregar um
	 * objeto do lado do MUITOS (N) (um objeto pedido/Order), o objeto do lado do UM
	 * (1) (cliente/User) é carregado automaticamente, por exemplo, se eu carregar
	 * um pedido que é o lado do muitos List<Order> orders = new ArrayList<>(); na
	 * entidade Client, como um pedido tem UM só cliente, esse cliente é
	 * automaticamente carregado e alinhado o objeto relacionado cliente no Json.
	 * 
	 * AGORA, o inverso por padrão não ocorre, ou seja, se eu carregar um objeto
	 * Cliente (UM), não vai aparecer os pedidos dele carregados (MUITOS) no Json. É
	 * o chamado lazy loading, onde o lado para muitos só é carregado se for
	 * CHAMADO, para evitar estouro de memória e tráfego de rede.
	 * 
	 * Quem solicita esse chamado é o Jackson, que é o serializador Json, ele
	 * solicita para ai sim o JPA carregar
	 * 
	 * Se eu colocasse @JsonIgnore em
	 * 
	 * @JsonIgnore
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "client_id") private User client;
	 * 
	 * Na classe Pedido (Order), ai sim, o cliente do pedido não seria carregado
	 * automaticamente do banco de dados
	 * 
	 * Agora se eu deixasse o @JsonIgnore nos pedidos e retira-se do cliente na
	 * lista de pedidos, ao carregar o cliente carregaria os pedidos, e o contrário
	 * não seria verdade.
	 * 
	 * Por fim, por padrão, vamos deixar o @JsonIgnore na classe 1, que tem o para
	 * muitos, no caso, nessa classe User (1) que tem associação com Order (n), ou
	 * seja, se eu carregar um usuário, não vai ser carregado automaticamente todos
	 * seus pedidos, que podem ser MUITOS. Não precisa por na Order, pois ela tem 1
	 * só cliente, então carregar esse cliente não pode gerar looping.
	 */
	@JsonIgnore
	/* 1 cliente para muitos pedidos */
	/*
	 * A associação OneToMany aparentemente é opcional, só é usado caso eu queira
	 * acessar um objeto do tipo usuário e acessar automaticamente os pedidos feitos
	 * por esse usuário
	 */
	/*
	 * mappedBy = "client" é o o EXATO nome do ATRIBUTO lá na classe Order que
	 * refere-se ao usuário (cliente), ou seja: Esse muito-para-um, lá do outro lado
	 * (Order) ele está mapeado por (mappedBy) "client" (atributo client da classe
	 * order)
	 * 
	 * A gente coloca o @JoinColumn no lado "One/Um", ou seja, no Order, porque o
	 * pedido "One" tem só 1 usuário, então salva o id do usuário lá na tabela de
	 * pedidos (Order)
	 */
	@OneToMany(mappedBy = "client")
	List<Order> orders = new ArrayList<>();

	public User() {

	}

	public User(Long id, String name, String email, String phone, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Order> getOrders() {
		return orders;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
