package org.project.admin.repository;

import org.junit.jupiter.api.Test;
import org.project.admin.config.JpaConfig;
import org.project.admin.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void givenBoard_whenSavesBoard_thenReturnsSavedBoard() {
        // Given
        Board board = createBoard();

        // When
        Board savedBoard = boardRepository.save(board);

        // Then
        assertThat(boardRepository.count()).isEqualTo(1);
    }

    @Test
    void givenSavedBoard_whenFindingBoardWithIsDeletedFalse_thenReturnsBoards() {
        // Given
        Board board = createBoard();
        Pageable pageable = createPageable();
        boardRepository.save(board);

        // When
        Page<Board> boards = boardRepository.findByIsDeletedFalse(pageable);

        // Then
        assertThat(boards.getContent()).hasSize(1);
    }

    private Board createBoard() {
        return Board.of("Test board", "Dummy board for test.");
    }

    private Pageable createPageable() {
        return PageRequest.of(0, 10, Sort.by("modifiedDate").descending());
    }

}