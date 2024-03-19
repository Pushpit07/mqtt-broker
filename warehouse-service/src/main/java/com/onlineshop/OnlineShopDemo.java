package com.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.onlineshop.controller.ItemGRpcService;

@SpringBootApplication
@ComponentScan(basePackages = "com.onlineshop.runner, com.onlineshop.controller")
public class OnlineShopDemo {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(OnlineShopDemo.class, args);

		// Start gRPC server
		System.out.println("Starting gRPC Server");
        Server server = ServerBuilder.forPort(9090).addService(new ItemGRpcService()).build();
        server.start();
        System.out.println("Server started at "+ server.getPort());
	    server.awaitTermination();
	}

}
