package com.hhhhhh.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.hhhhhh.mapper")
@ComponentScan(value = {"com.hhhhhh.service"})
public class ItemServantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServantApplication.class, args);
	}
}