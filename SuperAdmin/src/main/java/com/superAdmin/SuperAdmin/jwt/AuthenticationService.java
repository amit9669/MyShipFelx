package com.superAdmin.SuperAdmin.jwt;

import com.superAdmin.SuperAdmin.entity.request.LogInRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticate(LogInRequest input) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
    }
}
