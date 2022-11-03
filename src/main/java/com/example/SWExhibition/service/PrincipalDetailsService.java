package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.security.PrincipalDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private final HttpSession session;


    // 로그인
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(userId).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + userId));

        // 세션 키 값 설정
        session.setAttribute("user", new UsersDto(user.getId(), user.getUserId(), user.getPassword(), user.getNickname(), user.getRole()));

        /* 시큐리티 세션에 유저 정보 저장 */
        return new PrincipalDetails(user);
    }
}
