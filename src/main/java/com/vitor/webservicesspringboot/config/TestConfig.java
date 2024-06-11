package com.vitor.webservicesspringboot.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vitor.webservicesspringboot.entities.Category;
import com.vitor.webservicesspringboot.entities.Order;
import com.vitor.webservicesspringboot.entities.User;
import com.vitor.webservicesspringboot.entities.enums.OrderStatus;
import com.vitor.webservicesspringboot.repositories.CategoryRepository;
import com.vitor.webservicesspringboot.repositories.OrderRepository;
import com.vitor.webservicesspringboot.repositories.UserRepository;

/*Fala pro Spring que é uma classe de configuração*/
@Configuration
/*
 * O Spring vai rodar essa configuração somente quando estiver no perfil de
 * teste
 */
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	/*
	 * Com o Autowired, o próprio Spring na hora que estiver rodando a aplicação vai
	 * resolver essa dependência e associar uma instância de userRepository aqui
	 * dentro
	 */
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * Se eu estiver no profile "test" definido acima, tudo dentro da função run
	 * será executado assim que iniciar a aplicação, ou seja, o banco de dados já
	 * vai ter os usuários e pedidos aqui instanciados e salvos por meio de seeding
	 */
	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Electronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");

		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "98888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "97777777", "123456");

		/* O pedido já está tendo seu usuário passado na instanciação */
		/*
		 * Por que desse formato de hora? pra padronizar, é o padrão ISO 8601 UTC, o
		 * horário de Greenwich, time-zone GMT ai pode-se depois converter para o
		 * horário local
		 */
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T19:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
	}

}
