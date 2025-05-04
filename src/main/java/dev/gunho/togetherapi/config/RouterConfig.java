package dev.gunho.togetherapi.config;

import dev.gunho.togetherapi.handler.NaverHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(NaverHandler naverHandler) {
        return RouterFunctions
                .route(GET("/search/blog"), naverHandler::getBlogs)
                .andRoute(GET("/search/locals"), naverHandler::locals);
    }
}
