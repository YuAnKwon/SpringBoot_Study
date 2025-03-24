package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    // 댓글 조회
    public List<CommentDto> comments(Long articleId) {
//        // 1. 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 2. 엔티티 -> DTO 변환
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i); // 조회한 댓글 엔티티 하나씩 가져오기
//            CommentDto dto = CommentDto.createCommentDto(c); // 엔티티를 DTO로 변환
//            dtos.add(dto); // 변환한 DTO를 dtos 리스트에 삽입
//        }

        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) //엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 스트림을 List로 변환
    }

    // 댓글 생성
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId).orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    // 댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패! 해당 id의 댓글은 없습니다."));
        // 댓글 수정하기
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // dto로 변환하여 반환
        CommentDto updatedDto = CommentDto.createCommentDto(updated);
        return updatedDto;
    }

    // 댓글 삭제
    @Transactional
    public CommentDto delete(Long id){
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패! 해당 id의 댓글이 존재하지 않습니다."));
        // 댓글 삭제
        commentRepository.delete(target);
        // dto로 변환후 반환
        CommentDto deletedDto = CommentDto.createCommentDto(target);
        return deletedDto;
    }
}
