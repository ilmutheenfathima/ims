package com.company.ims.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityUtil {
    public static boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(role::equals);
    }
}
