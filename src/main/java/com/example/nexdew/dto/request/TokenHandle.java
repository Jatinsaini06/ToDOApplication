package com.example.nexdew.dto.request;

import com.example.nexdew.entities.Users;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Data
@RequiredArgsConstructor
public class TokenHandle implements UserDetails {

    private UUID userID;
    private  String userName;
    private final String password;
    private final Users users;
    private final List<GrantedAuthority> authorities;

    public TokenHandle(Users users, List<GrantedAuthority> authorities){
        this.users = users;
        this.userID = users.getUserId();
        this.userName = users.getUserName();
        this.password = users.getUserPassword();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userName = users.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
