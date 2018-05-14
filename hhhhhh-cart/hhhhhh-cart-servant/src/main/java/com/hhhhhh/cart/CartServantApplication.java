package com.hhhhhh.cart;

import com.alibaba.dubbo.container.Main;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
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
public class CartServantApplication implements CommandLineRunner {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CartServantApplication.class, args);
//        Main.main(args);
	}

	@Override
	public void run(String[] args) throws Exception {
        Thread.currentThread().join();
	}
}
