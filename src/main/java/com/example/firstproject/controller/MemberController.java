package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    // 로그인 페이지
    @GetMapping("/signup")
    public String newMembersForm(){
        return "members/new";
    }

    // email, password 받아 db에 저장.
    @PostMapping("/join")
    public String signUpPage(MemberForm memberform){
        //System.out.println(memberform.toString());
        log.info(memberform.toString());

        // 1. DTO 객체를 엔티티로 반환하기
        Member member = memberform.toEntity();
        //System.out.println(member.toString());
        log.info(member.toString());

        // 2. 엔티티를 리파지터리를 통해 DB에 저장.
        Member saved = memberRepository.save(member);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/members/"+saved.getId();
    }

    // 특정회원 조회
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    // 전체회원 조회
    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> memberEntityList = memberRepository.findAll();
        model.addAttribute("memberList",memberEntityList);
        return "members/index";
    }

    //수정 페이지로 이동
    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    // 데이터 갱신
    @PostMapping("/members/update")
    public String update(MemberForm form){
        log.info(form.toString());

        // form을 Entity로 변환
        Member memberEntity = form.toEntity();
        // id가 null인지 확인
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target !=null) {
            // 데이터 갱신
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/"+memberEntity.getId();
    }

    // 데이터 삭제
    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Member target = memberRepository.findById(id).orElse(null);
        // 삭제
        if(target != null){
            memberRepository.delete(target);
            // 삭제시 일회성메시지
            rttr.addFlashAttribute("msg","삭제되었습니다.");
        }
        return "redirect:/members";
    }
}
