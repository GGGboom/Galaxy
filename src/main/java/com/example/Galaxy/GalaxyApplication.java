package com.example.Galaxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.Galaxy.dao")
@SpringBootApplication
public class GalaxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxyApplication.class, args);
	}

}
