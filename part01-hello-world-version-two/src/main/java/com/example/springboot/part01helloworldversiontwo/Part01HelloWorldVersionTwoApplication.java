package com.example.springboot.part01helloworldversiontwo;

import com.example.springboot.part01helloworldversiontwo.webflux.EchoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Part01HelloWorldVersionTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Part01HelloWorldVersionTwoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(EchoHandler echoHandler) {
		return route(POST("/echo"), echoHandler::echo);
	}
}