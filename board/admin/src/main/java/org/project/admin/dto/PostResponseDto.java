package org.project.admin.dto;

import org.project.admin.domain.Post;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        Integer viewCount,
        Integer likeCount
) {

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getLikeCount());
    }

}
