package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("/signup")
    public String newMembersForm(){
        return "members/new";
    }

    @PostMapping("/join")
    public String joinMember(MemberForm form){
        System.out.println(form.toString());
        // 1. DTO 객체를 엔티티로 반환하기
        Member member = form.toEntity();
        System.out.println(member.toString());
        // 2. 엔티티를 리파지터리를 통해 DB에 저장.
        Member saved = memberRepository.save(member);
        System.out.println(saved.toString());
        return "";
    }
}
