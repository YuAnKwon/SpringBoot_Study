package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    // findAll() 메서드의 반환값은 기본적으로 Iterable 타입. 이를 ArrayList로 바꾸기 위해 오버라이드
    ArrayList<Article> findAll();
}
