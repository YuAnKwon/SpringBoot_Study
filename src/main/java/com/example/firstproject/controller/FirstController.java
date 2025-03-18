package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi") // url 요청 접수
    public String niceToMeetYou(Model model){ // 모델 객체 받아 오기
        model.addAttribute("username", "toeic"); // 모델 변수 등록.
        return "greetings"; // greetings.mustache 뷰 템플릿 페이지 반환.
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}