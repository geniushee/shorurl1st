package com.surl.first.global.securityConfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class SecurityUser extends User {
    private Long id;

    public SecurityUser(Long id, String username, String password, List<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.id=id;
    }

    public SecurityUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities){
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

}
