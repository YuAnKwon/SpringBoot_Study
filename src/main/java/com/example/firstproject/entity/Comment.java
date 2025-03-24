package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
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

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if(dto.getId() !=null){
            throw new IllegalArgumentException("댓글 생성실패 ! 댓글의 id가 없어야한다.");
        }
        if(dto.getArticleId() != article.getId()){
            throw new IllegalArgumentException("댓글 생성실패 ! 게시글의 id가 잘못되었다.");
        }
        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (dto.getId() != this.id){
            throw new IllegalArgumentException("댓글 수정실패 ! 잘못된 id가 입력되었습니다.");
        }
        // 객체 갱신
        if(dto.getNickname() != null){ // 수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname(); // 내용 반영
        }
        if(dto.getBody() != null){ // 수정할 본문 데이터가 있다면
            this.body = dto.getBody(); // 내용 반영
        }
    }
}
