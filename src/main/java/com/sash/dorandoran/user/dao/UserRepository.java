package com.sash.dorandoran.user.dao;

import com.sash.dorandoran.user.domain.AuthProvider;
import com.sash.dorandoran.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthProviderAndEmail(AuthProvider authProvider, String email);

}
