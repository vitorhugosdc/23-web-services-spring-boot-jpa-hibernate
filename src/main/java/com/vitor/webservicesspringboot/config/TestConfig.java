package com.vitor.webservicesspringboot.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vitor.webservicesspringboot.entities.Category;
import com.vitor.webservicesspringboot.entities.Order;
import com.vitor.webservicesspringboot.entities.OrderItem;
import com.vitor.webservicesspringboot.entities.Payment;
import com.vitor.webservicesspringboot.entities.Product;
import com.vitor.webservicesspringboot.entities.User;
import com.vitor.webservicesspringboot.entities.enums.OrderStatus;
import com.vitor.webservicesspringboot.repositories.CategoryRepository;
import com.vitor.webservicesspringboot.repositories.OrderItemRepository;
import com.vitor.webservicesspringboot.repositories.OrderRepository;
import com.vitor.webservicesspringboot.repositories.ProductRepository;
import com.vitor.webservicesspringboot.repositories.UserRepository;

/*Fala pro Spring que é uma classe de configuração*/
@Configuration
/*
 * O Spring vai rodar essa configuração somente quando estiver no perfil de
 * teste
 */
@Profile("test")
public class TestConfig implements CommandLineRunner {

	/*
	 * Com o Autowired, o próprio Spring na hora que estiver rodando a aplicação vai
	 * resolver essa dependência e associar uma instância de userRepository aqui
	 * dentro
	 */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

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

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		/* Adicionando as categorias dos produtos em memória (porque sim) */
		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);

		/*
		 * Salvando novamente com as associações feitas (lembrando que é diferente se eu
		 * tivesse trabalhando com SQL ao invés do JPA, pois aqui estamos trabalhando
		 * com orientação a objetos.
		 */
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

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

		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);

		/*
		 * Aqui temos uma PECULIARIDADE: Para salvar um objeto dependente em uma relação
		 * 1 para 1, a gente não chama o repository do próprio objeto dependente, a
		 * gente faz a associação de mão dupla em memória com o set da classe dominante
		 * e, chama o repository da própria classe dominante, que ele já salva.
		 * 
		 * Detalhe: nem repository a gente cria pro payment
		 */
		o1.setPayment(pay1);
		orderRepository.save(o1);
	}

}
