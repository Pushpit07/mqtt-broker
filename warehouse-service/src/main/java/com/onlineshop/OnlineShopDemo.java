package com.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.onlineshop.runner, com.onlineshop.controller")
public class OnlineShopDemo {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopDemo.class, args);
	}

}
