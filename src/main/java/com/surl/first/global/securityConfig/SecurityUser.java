package com.surl.first.global.securityConfig;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class SecurityUser extends User {
    private final Long id;

    private final String name;

    public SecurityUser(Long id, String username, String password, String name, List<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.id=id;
        this.name = name;
    }

    public SecurityUser(Long id, String username, String password, String name, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities){
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.name = name;
    }

}
