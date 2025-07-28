package org.project.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.admin.dto.BoardRequestDto;
import org.project.admin.dto.BoardResponseDto;
import org.project.admin.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void givenMultipleBoardsWhenGettingAllBoardsThenReturnsPageList() {
        // Given
        for (int i = 0; i < 35; i++) {
            boardService.saveBoard(new BoardRequestDto(null, "Test name " + i, "Test description " + i));
        }
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modifiedDate").descending());

        // When
        Page<BoardResponseDto> boards = boardService.getBoards(pageable);

        // Then
        assertThat(boards).isNotNull();
        assertThat(boards.getTotalElements()).isEqualTo(35);
        assertThat(boards.getTotalPages()).isEqualTo(4);
    }

    @DisplayName("Test getBoard()")
    @Test
    void givenBoardIdWhenGettingBoardByIdThenReturnsCorrespondingBoardResponseDto
            () {
        // Given
        BoardRequestDto requestDto = createBoardRequestDto();
        String name = requestDto.name();
        String description = requestDto.description();
        Long id = boardService.saveBoard(requestDto);

        // When
        BoardResponseDto board = boardService.getBoard(id);

        // Then
        assertThat(board).isNotNull();
        assertThat(board.name()).isEqualTo(name);
        assertThat(board.description()).isEqualTo(description);
    }

    @DisplayName("Test saveBoard()")
    @Test
    void givenBoardRequestDtoWhenSavingBoardThenReturnsBoardId() {
        // Given
        BoardRequestDto requestDto = createBoardRequestDto();

        // When
        Long id = boardService.saveBoard(requestDto);

        // Then
        assertThat(id).isNotNull();
        assertThat(boardRepository.findById(id)).isPresent();
    }

    @DisplayName("Test updateBoard()")
    @Test
    void givenUpdatedBoardNameWhenUpdatingBoardThenUpdatesAndReturnsNothing() {
        // Given
        BoardRequestDto requestDto = createBoardRequestDto();
        Long id = boardService.saveBoard(requestDto);
        BoardRequestDto updateRequestDto = new BoardRequestDto(null, "Updated test name", "Updated test description");

        // When
        boardService.updateBoard(id, updateRequestDto);
        BoardResponseDto board = boardService.getBoard(id);

        // Then
        assertThat(board).isNotNull();
        assertThat(board.name()).isEqualTo(updateRequestDto.name());
        assertThat(board.description()).isEqualTo(updateRequestDto.description());
    }

    @DisplayName("Test deleteBoard()")
    @Test
    void givenBoardIdWhenDeletingBoardThenDeletesAndReturnsNothing() {
        // Given
        Long id = boardService.saveBoard(createBoardRequestDto());

        // When
        boardService.deleteBoard(id);

        // Then
        assertThat(boardRepository.findById(id).get().getIsDeleted()).isTrue();
    }

    private BoardRequestDto createBoardRequestDto() {
        return new BoardRequestDto(null, "Test name", "Test description");
    }

}