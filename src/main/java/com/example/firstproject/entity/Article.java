package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id // 엔티티의 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 기능 추가 (DB가 숫자를 자동으로 매겨줌)
    private Long id;
    @Column // title 필드 선언. DB 테이블의 title 열과 연결됨.
    private String title;
    @Column
    private String content;


    //  수정할 내용이 있는 경우에만 동작하면 된다.
    public void patch(Article article) {
        //this는 patch() 메서드를 호출한 객체를 의미
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }
}
