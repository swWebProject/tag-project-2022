package com.example.SWExhibition.controller;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "/home";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginForm() {
        return "/login";
    }
    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new UsersDto());

        return "/signup";
    }

    // 회원가입 Form 전송
    @PostMapping("/usur/signup")
    public String signUp(UsersDto userDto) {
        usersService.signUp(userDto);

        return "redict:/user/login";
    }

}
