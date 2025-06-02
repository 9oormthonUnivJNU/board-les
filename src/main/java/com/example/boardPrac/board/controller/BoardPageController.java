package com.example.boardPrac.board.controller;

import com.example.boardPrac.board.model.BoardDto;
import com.example.boardPrac.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardPageController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String listPage(Model model) {
        List<BoardDto> boards = boardService.getAll();
        model.addAttribute("boards", boards);
        return "boardList";
    }
}
