package com.example.boardPrac.board.controller;

import com.example.boardPrac.board.db.BoardEntity;
import com.example.boardPrac.board.db.BoardRepository;
import com.example.boardPrac.board.model.BoardDto;
import com.example.boardPrac.board.model.BoardRequest;
import com.example.boardPrac.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping("")
    public BoardDto create(
            @Valid
            @RequestBody BoardRequest boardRequest
    ){
        return boardService.create(boardRequest);
    }


    @GetMapping("/id/{id}")
    public BoardDto view(
            @PathVariable Long id
    ){
        var entity = boardService.view(id);
        log.info("result : {}",entity);
        return entity;
    }

    @GetMapping("/ids/{id}")
    public List<BoardEntity> all(
            @PathVariable Long id
    ){
        return boardRepository.findAll();
    }

}


