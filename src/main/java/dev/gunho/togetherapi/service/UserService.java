package dev.gunho.togetherapi.service;

import dev.gunho.togetherapi.dto.Response;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<Response<?>> createUser(ServerRequest request);

    public Mono<Response<?>> existsUser(ServerRequest request);

    public Mono<Response<?>> login(ServerRequest request);

}
