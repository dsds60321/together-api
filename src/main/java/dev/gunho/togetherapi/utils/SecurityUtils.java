package dev.gunho.togetherapi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecurityUtils {

    // 현재 인증된 사용자 ID 가져오기
    public Mono<String> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .cast(String.class)
                .switchIfEmpty(Mono.empty());
    }

    // 현재 사용자의 등급 확인
    public Mono<Boolean> hasGrade(String requiredGrade) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(auth -> auth.getAuthorities().contains(
                        new SimpleGrantedAuthority("ROLE_" + requiredGrade)
                ))
                .defaultIfEmpty(false);
    }

    // 인증 여부 확인
    public Mono<Boolean> isAuthenticated() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::isAuthenticated)
                .defaultIfEmpty(false);
    }

}
