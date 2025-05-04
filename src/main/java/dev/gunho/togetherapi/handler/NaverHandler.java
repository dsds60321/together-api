package dev.gunho.togetherapi.handler;

import dev.gunho.togetherapi.dto.thirdparty.NaverApiResponse;
import dev.gunho.togetherapi.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NaverHandler {

    private final NaverService naverService;

    public Mono<ServerResponse> getBlogs(ServerRequest request) {
        return ServerResponse.ok()
                .body(naverService.getBlogs(request), NaverApiResponse.class);
    }

    // url scheme 응답
    public Mono<ServerResponse> locals(ServerRequest request) {
        return  ServerResponse.ok()
                .body(naverService.getLocals(request), NaverApiResponse.class);
    }
}
