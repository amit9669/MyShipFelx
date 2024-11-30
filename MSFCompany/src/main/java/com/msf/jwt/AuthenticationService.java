package com.msf.jwt;

import com.msf.entity.request.MsfLogIn;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticate(MsfLogIn request) {

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
    }
}
