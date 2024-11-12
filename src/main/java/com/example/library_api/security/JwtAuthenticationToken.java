package com.example.library_api.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public JwtAuthenticationToken(String username) {
        super(username, null);  // В аутентификационном токене пароль может быть null
    }

    // Можно добавить дополнительные данные, если нужно, например, роли
    public JwtAuthenticationToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, null, authorities);
    }
}

