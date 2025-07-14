package org.project.admin.dto;

public record PostRequestDto(
        String title,
        String content,
        Boolean isNotice
) {
}
