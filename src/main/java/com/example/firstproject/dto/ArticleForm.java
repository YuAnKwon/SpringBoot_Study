package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 생성자
@ToString
public class ArticleForm {
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    // 생성자 : @allargconstructor로 대신.
    // ToString : @tosting으로 대신함..

    // 엔티티로 변환
    public Article toEntity() {
        return new Article(null, title, content); //엔티티 반환
    }
}
