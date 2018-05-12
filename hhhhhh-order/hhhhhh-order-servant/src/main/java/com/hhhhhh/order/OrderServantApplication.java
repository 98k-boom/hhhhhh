package com.hhhhhh.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.hhhhhh.mapper")
public class OrderServantApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServantApplication.class, args);

	}
}
