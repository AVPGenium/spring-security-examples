package org.example.config.security;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public String setUserMailIfEmpty(String email) {
        return email == null ? "system@admin.ru" : email;
    }
}
