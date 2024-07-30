package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.presentation.dto.UserRequest;

public interface UserService {

    JwtResponse signUp(UserRequest request);

    JwtResponse signIn(UserRequest request);

    User findById(Long userId);

}
