package dev.gunho.togetherapi.config;

import dev.gunho.togetherapi.handler.NaverHandler;
import dev.gunho.togetherapi.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {
        return RouterFunctions
                .route(POST("/users"), userHandler::createUser)
                .andRoute(GET("/users"), userHandler::existsUser)
                .andRoute(POST("/login"), userHandler::login);
    }

    @Bean
    public RouterFunction<ServerResponse> naverRoute(NaverHandler naverHandler) {
        return RouterFunctions
                .route(GET("/search/blog"), naverHandler::getBlogs)
                .andRoute(GET("/search/locals"), naverHandler::locals);
    }
}
