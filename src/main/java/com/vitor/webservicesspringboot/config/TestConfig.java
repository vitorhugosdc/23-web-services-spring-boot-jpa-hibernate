package com.vitor.webservicesspringboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vitor.webservicesspringboot.entities.User;
import com.vitor.webservicesspringboot.repositories.UserRepository;

/*Fala pro Spring que é uma classe de configuração*/
@Configuration
/*
 * O Spring só vai rodar essa configuração somente quando estiver no perfil de
 * teste
 */
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "98888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "97777777", "123456");
		userRepository.saveAll(Arrays.asList(u1, u2));
	}

}
