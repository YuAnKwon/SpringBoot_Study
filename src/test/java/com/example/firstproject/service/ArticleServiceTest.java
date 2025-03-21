package com.example.firstproject.service;

import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스를 스프링 부트와 연동해테스트
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test // 해당 메서드가 테스트 코드임을 선언
    void index() {
        //1. 예상 데이터
        Article a = new Article(1L, "가가가가", "1111"); // 예상 데이터 객체로 저장.
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); // abc 합치기

        //2. 실제 데이터
        List<Article> articles = articleService.index();

        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1. 예상 데이터

        //2. 실제 데이터

        //3. 데이터 비교해 검증
    }
    @Test
    void show_실패() {
        //1. 예상 데이터

        //2. 실제 데이터

        //3. 데이터 비교해 검증
    }
}