package com.example.SWExhibition.security;

import com.example.SWExhibition.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Users user;

    public PrincipalDetails(Users user) {
        this.user = user;
    }

    //해당 유저의 권한을 가져오는 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }


    // 비밀번호를 가지고 오는 메소드
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 유저 ID를 가지고 오는 메소드
    @Override
    public String getUsername() {
        return user.getUserId();
    }
    //닉네임을 가지고 오는 메소드
    public String getNickname() {
        return user.getNickname();
    }

    // 계정 만료 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // 계정 비밀번호 변경 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 확인
    @Override
    public boolean isEnabled() {
        return true;
    }
}
