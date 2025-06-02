package com.example.boardPrac.post.controller;

import com.example.boardPrac.board.db.BoardRepository;
import com.example.boardPrac.post.db.PostEntity;
import com.example.boardPrac.post.model.PostDto;
import com.example.boardPrac.post.service.PostConverter;
import com.example.boardPrac.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostPageController {

    private final BoardRepository boardRepository;
    private final PostService postService;
    private final PostConverter postConverter;

    @GetMapping("/list/{boardId}")
    public String postListByBoard(
            @PathVariable Long boardId,
            Model model
    ) {
        var boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판이 존재하지 않습니다."));

        List<PostEntity> posts = boardEntity.getPostList();
        List<PostDto> postDtos = posts.stream().map(postConverter::toDto).toList();

        model.addAttribute("boardName", boardEntity.getBoardName());
        model.addAttribute("posts", postDtos);
        model.addAttribute("boardId", boardId);

        return "postList";
    }

    @GetMapping("/view/{postId}")
    public String postDetail(
            @PathVariable Long postId,
            Model model
    ) {
        var postEntity = postService.readOnlyView(postId);
        var dto = postConverter.toDto(postEntity);
        model.addAttribute("post", dto);
        return "postDetail";
    }

    @GetMapping("/write/{boardId}")
    public String writeForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "postForm";
    }




}
