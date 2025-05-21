package com.example.boardPrac.reply.db;

import com.example.boardPrac.post.db.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder    // 빌더 패턴을 적용 , 유연하게 객체 생성 가능
@Entity(name = "reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키(id) 값을 자동으로 생성하되, DB의 auto-increment 기능을 사용
    private Long id;

    @ManyToOne  //여러 개의 댓글(ReplyEntity)은 하나의 게시글(PostEntity)에 속함
    @ToString.Exclude   //Lombok의 @ToString이 포함된 경우, 이 필드를 toString() 출력에서 제외
    @JsonIgnore
    private PostEntity post;    // post => _id => post_id

    private String userName;

    private String password;

    private String status;

    private String title;

    @Column(columnDefinition = "TEXT")  // VARCHAR(255)보다 훨씬 더 많은 길이의 문자열을 저장가능
    private String content;

    private LocalDateTime repliedAt;


}