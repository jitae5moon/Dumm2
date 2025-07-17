package org.project.admin.dto;

import org.project.admin.domain.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        Boolean isNotice,
        Integer viewCount,
        Integer likeCount,
        String createdBy,
        LocalDateTime createdDate
) {

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getIsNotice(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCreatedBy(),
                post.getCreatedDate());
    }

}
