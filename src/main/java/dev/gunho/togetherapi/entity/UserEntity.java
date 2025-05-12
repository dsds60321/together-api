package dev.gunho.togetherapi.entity;

import dev.gunho.togetherapi.dto.user.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table("users")
public class UserEntity {

    @Id
    @Column("idx")
    private Long id;

    @Column("user_id")
    private String userId;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String grade; // "USER", "PREMIUM", "ADMIN" 값을 저장


    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public void setPassword(String encode) {
        this.password = encode;
    }
}
