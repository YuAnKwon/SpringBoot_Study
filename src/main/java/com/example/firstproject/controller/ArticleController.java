package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j // 로깅 기능임
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository; // articleRepository 객체 선언.

    // new.mustache 화면 반환
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    // 데이터 저장
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환하기
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); //article 엔티티를 저장해 saved 객체에 반환.
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId(); // 리다이렉트 (클라이언트의 요청을 받아 새로운 URL 주소로 재요청하라 지시)
    }

    // id로 데이터 조회
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){ // 매개변수로 id 받아오기
        log.info("id = "+id);
        // 1 id를 조회해 DB에서 데이터 갖고오기
        // 데이터를 갖고 오는 주체는 리파지터리.
        Article articleEntity = articleRepository.findById(id).orElse(null); //id 값으로 데이터를 찾을 때 없으면 null로 반환.
        //Optional 타입으로 반환.

        // 2 가져온 데이터 모델에 등록
        // MVC 패턴에 따라 조회한 데이터를 뷰 페이지에서 보여주기 위함.
        model.addAttribute("article",articleEntity); //article이라는 이름에 articleEntity 객체 등록.

        // 3 데이터를 사용자에게 보여주기 위한 뷰 페이지 만들고 반환
        return "articles/show";
    }

    // 모든 데이터 조회
    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지
        return "articles/index";
    }

    // 수정페이지로 이동
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    // 데이터 갱신
    @PostMapping("/articles/update")
    public String update(ArticleForm form){ // 매개변수로 DTO 받아오기
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2.1 DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 기존 데이터 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity); //엔티티를 DB에 저장 (갱신)
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    // 데이터 삭제
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!");

        //1. 삭제할 대상 갖고오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상 엔티티 삭제하기
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다.");
            // /articles에서 삭제메시지 보여줘야해서 index.mustache파일 헤더(header.mustache)에 삭제 메시지 추가.
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
