package dev.gunho.togetherapi.service;

import dev.gunho.togetherapi.dto.thirdparty.NaverApiResponse;
import dev.gunho.togetherapi.dto.thirdparty.NaverBlogItem;
import dev.gunho.togetherapi.dto.thirdparty.NaverLocalItem;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface NaverService {

    public Mono<NaverApiResponse<NaverBlogItem>> getBlogs(ServerRequest request);
    public Mono<NaverApiResponse<NaverLocalItem>> getLocals(ServerRequest request);
}
