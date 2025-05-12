package dev.gunho.togetherapi.repository;

import dev.gunho.togetherapi.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

    Mono<Boolean> existsByUserId(String userId);

    Mono<UserEntity> findByUserId(String userId);
}
