package com.example.boardPrac.board.model;


import com.example.boardPrac.post.model.PostDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDto {

    private Long id;

    private String boardName;

    private String status;

    private List<PostDto> postList = List.of();
}
/*
 * @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
 * 프론트와 JSON 데이터를 주고 받을때
 * - camelCase를 주로 쓰는 JAVA와 달리 JavaScript와 python 등 다른 환경은 snake_case를 자주 씀
 * - API 응답의 Key이름을 프론트에서 기대하는 snake_case로 자동 변환하고 싶을 때 유용
 * 일관된 JSON 포맷 유지가 중요할 때
 * - API 문서 작성 시 또는 외부에 API를 제공하는 경우
 * - JSON key가 camelCase와 snake_case가 섞이면 가독성과 신뢰도가 하락하므로 이때 사용
 */