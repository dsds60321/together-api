package dev.gunho.togetherapi.client;

import dev.gunho.togetherapi.dto.thirdparty.*;
import dev.gunho.togetherapi.mapper.NaverApiRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class NaverClient {

    @Qualifier("naver")
    private final WebClient naverClient;

    private final NaverApiRequestMapper naverApiRequestMapper;

    @Value("${thirdparty.naver.url.blog}")
    private String blogUrl;

    @Value("${thirdparty.naver.url.local}")
    private String localsUrl;

    public Mono<NaverApiResponse<NaverBlogItem>> searchBlogs(ServerRequest request) {
        NaverApiRequest naverApiRequest = naverApiRequestMapper.toNaverApiRequest(request);
        return execute(naverApiRequest, blogUrl,
                new ParameterizedTypeReference<NaverApiResponse<NaverBlogItem>>() {},
                builder -> builder
                        .queryParam("query", naverApiRequest.getQuery())
                        .queryParam("display", naverApiRequest.getDisplay())
                        .queryParam("start", naverApiRequest.getStart())
                        .queryParam("sort", naverApiRequest.getSort()));
    }

    public Mono<NaverApiResponse<NaverLocalItem>> searchLocals(ServerRequest request) {
        NaverApiRequest naverApiRequest = naverApiRequestMapper.toNaverApiRequest(request);
        return execute(naverApiRequest, localsUrl,
                new ParameterizedTypeReference<NaverApiResponse<NaverLocalItem>>() {},
                builder -> builder
                        .queryParam("query", naverApiRequest.getQuery())
                        .queryParam("display", naverApiRequest.getDisplay())
                        .queryParam("start", naverApiRequest.getStart()));
    }

    /**
     * Naver api 공통 함수
     */
    private <T> Mono<NaverApiResponse<T>> execute(
            NaverApiRequest naverApiRequest,
            String url,
            ParameterizedTypeReference<NaverApiResponse<T>> typeReference,
            Function<UriBuilder, UriBuilder> uriFunction) {

        return naverClient.get()
                .uri(uriBuilder -> uriFunction.apply(uriBuilder.path(url)).build())
                .header(HttpHeaders.ACCEPT, "application/json")
                .retrieve()
                .bodyToMono(typeReference)
                .map(response -> {
                    if (response.getItems() != null) {
                        response.getItems().forEach(item -> {
                            if (item instanceof NaverBlogItem blogItem) {
                                blogItem.setId(UUID.randomUUID().toString().substring(0,5));
                            } else if (item instanceof NaverLocalItem localItem) {
                                localItem.setId(UUID.randomUUID().toString().substring(0,5));
                            }
                        });
                    }
                    return response;
                });
    }
}