package dev.gunho.togetherapi.handler;

import dev.gunho.togetherapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return userService.createUser(request)
                .flatMap(response -> {
                    if (response.isSuccess()) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response);
                    } else {
                        return ServerResponse.status(response.getCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response);
                    }
                });
    }

    public Mono<ServerResponse> existsUser(ServerRequest request) {
        return userService.existsUser(request)
                .flatMap(response -> {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response);
                });
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return userService.login(request)
                .flatMap(response -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                });
    }

}
