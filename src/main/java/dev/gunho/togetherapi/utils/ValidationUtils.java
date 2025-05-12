package dev.gunho.togetherapi.utils;

import dev.gunho.togetherapi.dto.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {

    public static <T> Mono<Response<?>> validate(T object, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return Mono.just(Response.builder()
                    .success(false)
                    .message(errorMessage)
                    .code(400)
                    .build());
        }
        return null; // 유효성 검사 통과
    }

}
