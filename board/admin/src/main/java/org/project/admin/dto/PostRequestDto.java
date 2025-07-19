package org.project.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record PostRequestDto(
        Long id,
        @NotBlank(message = "Title can't be blank.")
        String title,
        @NotBlank(message = "Content can't be blank.")
        String content,
        Boolean isNotice
) {
}
