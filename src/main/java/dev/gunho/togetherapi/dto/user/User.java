package dev.gunho.togetherapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class User {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String csrf;

        @NotBlank(message = "사용자 ID는 필수입니다.")
        @Size(min = 4, max = 20, message = "사용자 ID는 4~20자 사이여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "사용자 ID는 영문자, 숫자, 언더스코어만 포함할 수 있습니다.")
        private String userId;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(min = 2, max = 20, message = "닉네임은 2~20자 사이여야 합니다.")
        private String nickname;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String userId;
        private String nickname;
        private String email;
    }
}
