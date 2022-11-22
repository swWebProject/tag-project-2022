package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.entity.Users;
import com.example.SWExhibition.repository.RatingsRepository;
import com.example.SWExhibition.repository.UsersRepository;
import com.example.SWExhibition.repository.WantingsRepository;
import com.example.SWExhibition.repository.WatchingsRepository;
import com.example.SWExhibition.security.PrincipalDetails;
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
    private final RatingsRepository ratingsRepository;
    private final WatchingsRepository watchingsRepository;
    private final WantingsRepository wantingsRepository;

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

    // 회원 정보 수정
    public Users updateUser(PrincipalDetails principalDetails, UsersDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users update = usersRepository.findByUserId(principalDetails.getUsername()).orElseThrow(()-> {
            return new IllegalArgumentException("로그인 필요");
        });    // 현재 로그인 중인 유저 정보

        String password = passwordEncoder.encode(dto.getPassword());    // 비밀번호 암호화
        String nickname = dto.getNickname();

        // 닉네임 변경
        if (update.getNickname() != nickname)
            update.setNickname(nickname);

        // 비밀번호 업데이트
        update.setPassword(password);

        log.info(update.toString());

        return usersRepository.save(update);
    }

    public Users deleteUser(String userId) {
        Users deleted = usersRepository.findByUserId(userId).orElse(null);   // 삭제할 유저 entity
        if (deleted != null) {
            ratingsRepository.deleteByUser(deleted);    // 해당 유저의 모든 평점 정보 삭제
            wantingsRepository.deleteByUser(deleted);   // 해당 유저의 모든 보고 싶은 영화 정보 삭제
            watchingsRepository.deleteByUser(deleted);  // 해당 유저의 모든 보는 중인 영화 정보 삭제
            usersRepository.delete(deleted);    // 해당 유저 정보 삭제
            log.info(deleted.toString());
        }

        return deleted;
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
