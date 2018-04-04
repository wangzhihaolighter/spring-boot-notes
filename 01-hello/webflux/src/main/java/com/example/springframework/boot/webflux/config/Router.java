package com.example.springframework.boot.webflux.config;

import com.example.springframework.boot.webflux.handler.SimpleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 路由
 */
@Configuration
public class Router {

    @Bean
    public RouterFunction<?> routerFunction(SimpleHandler simpleHandler) {
        return route(
                GET("/"), simpleHandler::welcome).and(route(
                POST("/echo"), simpleHandler::echo)
        );
    }

}