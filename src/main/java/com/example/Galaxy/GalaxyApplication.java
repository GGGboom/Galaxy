package com.example.Galaxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cloud.config.server.EnableConfigServer;

@MapperScan("com.example.Galaxy.dao")
@SpringBootApplication
@EnableCaching//开启缓存
//@EnableConfigServer
public class GalaxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxyApplication.class, args);
	}

}
