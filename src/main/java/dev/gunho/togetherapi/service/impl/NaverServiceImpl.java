package dev.gunho.togetherapi.service.impl;

import dev.gunho.togetherapi.client.NaverClient;
import dev.gunho.togetherapi.dto.thirdparty.NaverApiResponse;
import dev.gunho.togetherapi.dto.thirdparty.NaverBlogItem;
import dev.gunho.togetherapi.dto.thirdparty.NaverLocalItem;
import dev.gunho.togetherapi.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NaverServiceImpl implements NaverService {

    private final NaverClient naverClient;

    @Override
    public Mono<NaverApiResponse<NaverBlogItem>> getBlogs(ServerRequest request) {
        return naverClient.searchBlogs(request);
    }

    @Override
    public Mono<NaverApiResponse<NaverLocalItem>> getLocals(ServerRequest request) {
        return naverClient.searchLocals(request);
    }
}
