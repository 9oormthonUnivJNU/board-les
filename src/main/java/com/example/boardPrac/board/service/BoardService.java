package com.example.boardPrac.board.service;


import com.example.boardPrac.board.db.BoardEntity;
import com.example.boardPrac.board.db.BoardRepository;
import com.example.boardPrac.board.model.BoardDto;
import com.example.boardPrac.board.model.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;

    public BoardDto create(BoardRequest boardRequest) {
        var entity = BoardEntity.builder()
                .boardName(boardRequest.getBoardName())
                .status("REGISTERED")
                .build();

        return boardConverter.toDto(boardRepository.save(entity));
    }

    public BoardDto view(Long id) {
        //id(게시판 고유id)에 해당하는 게시판이 없으면 예외 발생
        var entity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        return boardConverter.toDto(entity);
    }

    public List<BoardDto> getAll() {
        return boardRepository.findAll().stream()
                .map(boardConverter::toDto)
                .collect(Collectors.toList());
    }
}

