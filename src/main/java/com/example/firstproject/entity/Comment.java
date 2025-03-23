package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가기킴
    private Long id; //대표키

    @ManyToOne // 댓글과 게시글은 다대일 관계
    @JoinColumn(name="article_id") // 왜래키. Article 엔티티의 기본키(id)와 매핑.
    private Article article; //해당 댓글의 부모 게시글

    @Column
    private String nickname; // 댓글 단사람
    @Column
    private String body; //댓글 본문
}
