package dev.gunho.togetherapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${thirdparty.naver.url.base}")
    private String naverBaseUrl;

    @Value("${thirdparty.naver.client}")
    private String clientId;

    @Value("${thirdparty.naver.secret}")
    private String clientSecret;

    @Bean
    @Qualifier("naver")
    public WebClient naver() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10));

        return WebClient.builder()
                .baseUrl(naverBaseUrl)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(logRequest("naver"))
                .filter(logResponse("naver"))
                .build();
    }

    private ExchangeFilterFunction logRequest(String apiName) {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logger.info("{} - Request: {} {}", apiName, clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse(String apiName) {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logger.info("{} - Response: {} ", apiName, clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }

}
