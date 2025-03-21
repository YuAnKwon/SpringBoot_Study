package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    // GET
    public List<Article> index() {
        return articleRepository.findAll();
    }

    // GET
    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    public Article create(ArticleForm dto){
        Article article = dto.toEntity();
        // article 객체에 id 가 존재한다면 null 반환 (기존에있던걸 수정하라는요청이라서)
        if (article.getId() !=null){
            return null;
        }
        return articleRepository.save(article);
    }

    //PATCH
    public Article update(Long id, ArticleForm dto){
        //1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity(); //수정할 데이터
        log.info("id: {}, article: {}", id, article.toString());

        //2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null); // 기존데이터

        //3. 잘못된 요청 처리하기
        if(target == null || id!= article.getId()){
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id : {}, article: {}", id,article.toString());
            return null;
        }

        //4. 업데이트 및 정상응답(200)하기
        target.patch(article); // 기존 데이터(target)를 새로운 데이터(article)로 업데이트
        Article updated = articleRepository.save(target); // 업데이트된 객체 저장 (target을 저장해야 DB 업데이트됨)

        return updated;
    }

    // Delete
    public Article delete(Long id){
        // 1. 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 엔티티가 없어서 요청이 잘못됐을 경우 처리
        if(target == null){
            return null;
        }

        //3. 삭제하고 정상응답 반환하기.
        articleRepository.delete(target);
        return target; //build메서드는 body(null)과 같다.
    }

    @Transactional //해당 메서드는 하나의 트랜잭션으로 묶인다. (실패하더라도 롤백함)
    public List<Article> createAricles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환하기
        // dto를 스트림화함. map()으로 dto가 하나하나 올때마다 to.Entity()를 수행해 매핑하고 리스트로 묶기.
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());

        // 2. 엔티티 묶음을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 강제로 예외 상황 발생시키기
        articleRepository.findById(-1L).orElseThrow(()-> new IllegalArgumentException("결제 실패 !"));
        return articleList;
    }
}
