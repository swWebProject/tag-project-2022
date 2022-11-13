package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;

    // 회원가입
    @Transactional
    public void signUp(UsersDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // password를 암호화 한 뒤 dp에 저장

        Users join = userDto.toEntity();

        // 권한 설정
        if (join.getUserId().equals("admin863"))
            join.setRole("ROLE_ADMIN");
        join.setRole("ROLE_MEMBER");   // 기본 권한

        // 중복이 발생하면 종료
        if (userIdOverlopCheck(join.getUserId()) && nicknameOverlopCheck(join.getNickname())) {
            log.info("아이디 또는 닉네임 중복 발생!");
            return;
        }



        log.info(join.toString());

        usersRepository.save(join);
    }

    // 회원가입 시, 유효성 체크
    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        // 유효성 검사에 실패한 필드 목록
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    // userId 값 중복 확인
    @Transactional(readOnly = true)
    public boolean userIdOverlopCheck(String userId) {
        return usersRepository.existsByUserId(userId);
    }

    // nickname 값 중복 확인
    @Transactional(readOnly = true)
    public  boolean nicknameOverlopCheck(String nickname) {
        return usersRepository.existsByNickname(nickname);
    }

    public Optional<Users> findUser(String userId) {
        return usersRepository.findByUserId(userId);
    }
}
