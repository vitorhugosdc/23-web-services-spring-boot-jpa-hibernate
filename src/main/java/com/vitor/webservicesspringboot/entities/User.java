package com.vitor.webservicesspringboot.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
