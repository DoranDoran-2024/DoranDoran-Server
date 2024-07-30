package com.sash.dorandoran.user.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }
}
