package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    // 회원가입
    @Transactional
    public String signUp(UsersDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // password를 암호화 한 뒤 dp에 저장

        Users join = userDto.toEntity();
        validateDuplicateUser(join);

        return usersRepository.save(join).getId();
    }

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Optional<Users> userWapper = usersRepository.findByid(id);
        Users user = userWapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 여기서는 간단하게 username이 'admin'이면 admin권한 부여
        if ("admin".equals(id)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }

        // 아이디, 비밀번호를 매개변수로 User를 만들어 반환해준다.
        return new User(user.getId(), user.getPassword(), authorities);
    }

    // ID 중복 검사
    private void validateDuplicateUser(Users user) {
        Users findUser = usersRepository.findById(user.getId()).orElse(null);

        if (findUser != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
