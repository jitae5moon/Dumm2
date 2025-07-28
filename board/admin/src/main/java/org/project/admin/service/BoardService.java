package org.project.admin.service;

import lombok.RequiredArgsConstructor;
import org.project.admin.domain.Board;
import org.project.admin.dto.BoardRequestDto;
import org.project.admin.dto.BoardResponseDto;
import org.project.admin.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardResponseDto> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardResponseDto::from);
    }

    public BoardResponseDto getBoard(Long id) {
        return BoardResponseDto.from(boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No board found with id: " + id)));
    }

    public Long saveBoard(BoardRequestDto boardRequestDto) {
        return boardRepository.save(Board.of(boardRequestDto.name(), boardRequestDto.description())).getId();
    }

    public void updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No board found with id: " + id));

        board.setName(boardRequestDto.name());
        board.setDescription(boardRequestDto.description());
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No board found with id: " + id));

        board.setIsDeleted(true);
    }

}
