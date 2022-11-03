package com.example.SWExhibition.controller;

import com.example.SWExhibition.dto.UsersDto;
import com.example.SWExhibition.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService usersService;

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "/home/home";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginForm(@RequestParam(value = "error", required = false)String error,
                            @RequestParam(value = "exception", required = false)String exception,
                            Model model)
    {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "/login/login";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signUpForm() {
        return "/join/signup";
    }

    // 회원가입 Form 전송
    @PostMapping("/user/signup")
    public String signUp(@Valid UsersDto userDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("user", userDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = usersService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/join/signup";
        }

        usersService.signUp(userDto);

        return "redirect:/user/login";
    }

}
