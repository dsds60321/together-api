package dev.gunho.togetherapi.service.impl;

import dev.gunho.togetherapi.dto.Response;
import dev.gunho.togetherapi.dto.user.Login;
import dev.gunho.togetherapi.dto.user.User;
import dev.gunho.togetherapi.entity.UserEntity;
import dev.gunho.togetherapi.mapper.UserMapper;
import dev.gunho.togetherapi.repository.UserRepository;
import dev.gunho.togetherapi.service.UserService;
import dev.gunho.togetherapi.utils.JwtUtil;
import dev.gunho.togetherapi.utils.ValidationUtils;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Response<?>> createUser(ServerRequest request) {
        return request.bodyToMono(User.Request.class)
                .flatMap(userRequest -> {
                    Mono<Response<?>> validationResponse = ValidationUtils.validate(userRequest, validator);
                    if (validationResponse != null) {
                        return validationResponse;
                    }

                    return userRepository.existsByUserId(userRequest.getUserId())
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.just(Response.fail("이미 등록된 사용자 ID 입니다.", 400));
                                }

                                UserEntity userEntity = userMapper.toEntity(userRequest);
                                userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

                                return userRepository.save(userEntity)
                                        .map(savedUser -> {
                                            log.info("저장된 사용자 ID : {} ", savedUser.getUserId());
                                            User.Response response = userMapper.toResponse(savedUser);
                                            return Response.success(response);
                                        });
                            });
                })
                .onErrorResume(e -> {
                    log.error("사용자 등록 중 오류 발생: {}", e.getMessage(), e);
                    return Mono.just(Response.fail("사용자 등록 처리 중 오류가 발생했습니다: " + e.getMessage(), 500));
                });
    }

    @Override
    public Mono<Response<?>> existsUser(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam("userId"))
                .switchIfEmpty(Mono.just(""))
                .flatMap(userId -> {
                    if (userId.isEmpty()) {
                        return Mono.just(Response.fail("사용자 ID는 필수 파라미터입니다.", 400));
                    }

                    return userRepository.existsByUserId(userId)
                            .map(exists -> {
                                if (exists) {
                                    // 이미 존재하면 false(사용 불가)를 데이터로 전달
                                    return Response.success("이미 존재하는 아이디입니다.", false);
                                } else {
                                    // 존재하지 않으면 true(사용 가능)를 데이터로 전달
                                    return Response.success("사용 가능한 아이디입니다.", true);
                                }
                            });
                });
    }

    @Override
    public Mono<Response<?>> login(ServerRequest request) {
        return request.bodyToMono(Login.Request.class)
                .flatMap(loginRequest -> {
                    log.info("로그인 요청: {}", loginRequest);
                    if (loginRequest.getUserId() == null || loginRequest.getUserId().isEmpty()) {
                        return Mono.just(Response.fail("사용자 ID는 필수 입력 항목입니다.", 400));
                    }
                    if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                        return Mono.just(Response.fail("비밀번호는 필수 입력 항목입니다.", 400));
                    }

                    return userRepository.findByUserId(loginRequest.getUserId())
                            .flatMap(user -> {
                                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                                    String grade = user.getGrade() != null ? user.getGrade() : "USER";

                                    // JWT 토큰 생성
                                    String token = jwtUtil.generateToken(user.getUserId(), grade);

                                    Login.Response loginResponse = Login.Response.builder()
                                            .token(token)
                                            .userId(user.getUserId())
                                            .nickname(user.getNickname())
                                            .email(user.getEmail())
                                            .grade(grade)
                                            .build();

                                    return Mono.just(Response.success(loginResponse));


                                } else {
                                    return Mono.just(Response.fail("비밀번호가 일치하지 않습니다.", 400));
                                }
                            })
                            .switchIfEmpty(Mono.just(Response.fail("존재하지 않는 사용자입니다.", 404)));

                });
    }
}