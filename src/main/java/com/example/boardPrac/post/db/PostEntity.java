package com.example.boardPrac.post.db;

import com.example.boardPrac.board.db.BoardEntity;
import com.example.boardPrac.reply.db.ReplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 전략 -> MySQL에서 흔히 사용하는 IDENTITY
    private Long id;

    @ManyToOne  //여러 게시글은 하나의 게시판(BoardEntity)에. 조인 컬럼명은 board_id로 명시
    @JsonIgnore // 순환 참조 방지 및 toString() 재귀 방지 용도로 사용
    @ToString.Exclude
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity; //시스템상 board+ _id => board_id

    private String userName;

    private String password;

    private String email;

    private String status;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime postedAt;

    @OneToMany(
            mappedBy = "post"
    )
    @Builder.Default
    private List<ReplyEntity> replyList = new ArrayList<>();


}