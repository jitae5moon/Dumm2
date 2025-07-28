package org.project.admin.dto;

import org.project.admin.domain.Board;

import java.time.LocalDateTime;

public record BoardResponseDto(
        Long id,
        String name,
        String description,
        String createdBy,
        LocalDateTime createdDate
) {

    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(
                board.getId(),
                board.getName(),
                board.getDescription(),
                board.getCreatedBy(),
                board.getCreatedDate()
        );
    }

}
