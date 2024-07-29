package com.sash.dorandoran.user.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_provider", length = 10)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "level", length = 10)
    @Enumerated(EnumType.STRING)
    private UserLevel level;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(AuthProvider authProvider, UserLevel level, String email, Role role) {
        this.authProvider = authProvider;
        this.level = level;
        this.email = email;
        this.role = role;
    }
}
