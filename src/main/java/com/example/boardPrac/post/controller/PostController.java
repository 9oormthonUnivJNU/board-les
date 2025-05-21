package com.example.boardPrac.post.controller;

import com.example.boardPrac.common.Api;
import com.example.boardPrac.post.model.PostRequest;
import com.example.boardPrac.post.model.PostViewRequest;
import com.example.boardPrac.post.service.PostService;
import org.springframework.data.domain.Pageable;
import com.example.boardPrac.post.db.PostEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public PostEntity create(
            @Valid
            @RequestBody PostRequest postRequest
    ){
        return postService.create(postRequest);
    }

    @PostMapping("/view")
    public PostEntity view(
            @Valid
            @RequestBody PostViewRequest postViewRequest
    ){
        var entity = postService.view(postViewRequest);
        return entity;
    }


    @GetMapping("/all")
    public Api<List<PostEntity>> list(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return postService.all(pageable);
    }

    @PostMapping("/delete")
    public void delete(
            @Valid
            @RequestBody PostViewRequest postViewRequest
    ){
        postService.delete(postViewRequest);
    }

    @PutMapping("/update")
    public PostEntity update(
            @Valid
            @RequestBody PostRequest postRequest
    ){
        return postService.update(postRequest);
    }

}
