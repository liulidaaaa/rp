package com.rp.largegarbage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.rp.largegarbage")
@EnableJpaRepositories(basePackages = "com.rp.largegarbage.dao")
@EntityScan(basePackages = "com/rp/largegarbage/entity")
public class LargegarbageApplication {

	public static void main(String[] args) {
		SpringApplication.run(LargegarbageApplication.class, args);
	}

}
