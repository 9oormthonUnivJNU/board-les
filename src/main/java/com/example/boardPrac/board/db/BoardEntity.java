package com.example.boardPrac.board.db;

import com.example.boardPrac.post.db.PostEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder    //빌더 패턴 -> 유연한 객체 생성에 도움
@ToString
@Entity(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    private String status;


    @OneToMany(
            mappedBy = "boardEntity"
    )
    @Builder.Default
//    @Builder를 사용하면 기본값이 무시되는데, 명시적으로 기본값을 유지하려면 필요한 애노테이션
    @Where(clause = "status = 'REGISTERED'")
//    서비스/레파지토리 클래스에서 따로 조건을 걸지 않아도 자동 필터링
//    상태가 REGISTERED인 것만 항상 가져오도록 고정
    private List<PostEntity> postList = List.of();

}
/*
 *@Builder
 *converter에서 DTO로부터 Entity를 만들 때 빌더 패턴을 사용하면 선택적 필드 설정이 가능함
 *JPA가 직접 채워주는 엔티티로만 쓰고, 별도로 객체 생성을 안한다면 @Builder 필요없음
 */

/*
 * lombok의 빌더는 모든 필드에 대해 null을 기본으로 설정함.
 * @Builder만 사용하면 null값이 들어가서 NullPointerException이 발생할 수 있음
 * @Builder.Default를 쓰면 기본값으로 유지 가능
 *
 * 즉 -> NullPointerException 방지 + 컬렉션 타입 초기화 유지
 */