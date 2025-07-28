package org.project.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardRequestDto(
        Long id,
        @NotBlank(message = "Name can't be blank.")
        String name,
        String description
) {
}
