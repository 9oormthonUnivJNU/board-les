package com.example.boardPrac.post.service;

import com.example.boardPrac.board.db.BoardRepository;
import com.example.boardPrac.common.Api;
import com.example.boardPrac.common.Pagination;
import com.example.boardPrac.exception.BusinessException;
import com.example.boardPrac.exception.ErrorCode;
import com.example.boardPrac.post.db.PostEntity;
import com.example.boardPrac.post.db.PostRepository;
import com.example.boardPrac.post.model.PostRequest;
import com.example.boardPrac.post.model.PostViewRequest;
import com.example.boardPrac.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    public PostEntity readOnlyView(Long postId) {
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postId, "REGISTERED")
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다: " + postId));
    }

    public PostEntity create(
            PostRequest postRequest
    ){
        var boardEntity = boardRepository.findById(postRequest.getBoardId()).get(); // << 임시 고정

        var entity = PostEntity.builder()
                .boardEntity(boardEntity)
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build()
                ;

        return postRepository.save(entity);
    }

    /*  요청 들어온 게시글 ID로 DB에서 게시글 조회
     *  게시글 없다면 -> POST_NOT_FOUND
     *  게시글 있다면 -> 비밀번호 비교
     *  비밀번호 틀리면 -> PASSWORD_MISMATCH
     *  모두 통과 -> 게시글 리턴
     */
    public PostEntity view(PostViewRequest request) {
        PostEntity post = postRepository.findFirstByIdAndStatusOrderByIdDesc(
                request.getPostId(), "REGISTERED"
        ).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        //게시글이 없으면 BusinessException 예외 발생

        if (!post.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        return post;
    }


    public Api<List<PostEntity>> all(Pageable pageable) {
        var list = postRepository.findAll(pageable);

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build()
                ;

        var response = Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pagination(pagination)
                .build();

        return response;
    }

    public void delete(PostViewRequest postViewRequest) {
        postRepository.findById(postViewRequest.getPostId())
                .map( it -> {
                    // entity 존재
                    if(!it.getPassword().equals(postViewRequest.getPassword())){
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;
                }).orElseThrow(
                        ()-> {
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다 : "+postViewRequest.getPostId());
                        }
                );
    }

    public PostEntity update(PostRequest postRequest) {

        return postRepository.findById(postRequest.getId())
                .map(entity -> {

                    if (!entity.getPassword().equals(postRequest.getPassword())) {
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, entity.getPassword(), postRequest.getPassword()));
                    }

                    entity.setTitle(postRequest.getTitle());
                    entity.setContent(postRequest.getContent());

                    return postRepository.save(entity);

                }).orElseThrow(() -> {
                    return new RuntimeException("해당 게시글이 존재 하지 않습니다 : " + postRequest.getId());
                });
    }

}