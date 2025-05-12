package dev.gunho.togetherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private T data;
    private String message;
    private int code;
    private boolean success;


    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .data(data)
                .success(true)
                .build();
    }

    public static <T> Response<T> success(String message, boolean success) {
        return Response.<T>builder()
                .message(message)
                .success(success)
                .build();
    }

    public static <T> Response<T> fail(String message, int code) {
        return Response.<T>builder()
                .message(message)
                .code(code)
                .success(false)
                .build();
    }
}
