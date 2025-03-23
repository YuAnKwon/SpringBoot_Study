package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //2. 실제 데이터
        Article article = articleService.show(id);
        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지않는_id입력() {
        //1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.show(id);
        //3. 데이터 비교해 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공_title과_content만있는_dto입력() {
        //1. 예상 데이터
        String title = "라라라라";
        String content = "4444";

        ArticleForm dto = new ArticleForm(null, title, content); //dto 생성
        Article expected = new Article(4L, title, content); //예상 데이터 저장

        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto입력() {
        //1. 예상 데이터
        ArticleForm dto = new ArticleForm(4L, "라라라라", "4444");
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 데이터 비교해 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_존재하는id와_title_content가있는_dto입력() {
        //1. 예상 데이터
        Long id = 3L;
        ArticleForm dto = new ArticleForm(id, "가나다라", "1234");
        Article article = new Article(id, "가나다라","1234");
        //2. 실제 데이터
        Article expected = articleService.update(id,dto);
        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는id와_title만있는_dto입력() {
        //1. 예상 데이터
        Long id = 2L;
        ArticleForm dto = new ArticleForm(id, "가나다라", null);
        Article article = new Article(id, "가나다라","2222");
        //2. 실제 데이터
        Article expected = articleService.update(id,dto);
        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지않는_id의_dto입력() {
        //1. 예상 데이터
        Long id = -1L;
        ArticleForm dto = new ArticleForm(id, "가나다라", "1234");
        Article article = null;
        //2. 실제 데이터
        Article expected = articleService.update(id,dto);
        //3. 데이터 비교해 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는id_입력() {
        //1. 예상 데이터
        Long id = 3L;
        String title = "다다다다";
        String content = "3333";
        Article article = new Article(id, title, content);
        //2. 실제 데이터
        Article expected = articleService.delete(id);
        //3. 데이터 비교해 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id입력() {
        //1. 예상 데이터
        Long id = -1L;
        Article article = null;
        //2. 실제 데이터
        Article expected = articleService.delete(id);
        //3. 데이터 비교해 검증
        assertEquals(article, expected);
    }
}