package com.graphql.intro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.graphql.intro.repo")  // Ensure correct package scan
@ComponentScan(basePackages = "com.graphql.intro") // Ensure scanning
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("Available Spring Beans:");
//			for (String beanName : ctx.getBeanDefinitionNames()) {
//				System.out.println(beanName);
//			}
//		};
//	}
}