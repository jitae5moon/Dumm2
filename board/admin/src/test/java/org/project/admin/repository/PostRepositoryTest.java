package org.project.admin.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.admin.config.JpaConfig;
import org.project.admin.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void givenPost_whenSavesPost_thenReturnsSavedPost() {
        // Given
        Post post = createPost();

        // When
        Post savedPost = postRepository.save(post);

        // Then
        assertThat(postRepository.findAll()).hasSize(1);
        assertThat(postRepository.findById(savedPost.getId()).get().getCreatedBy()).isEqualTo("TEST_ADMIN");
    }

    @Test
    void givenSavedPost_whenFindingPostWithIsDeletedFalse_thenReturnsPost() {
        // Given
        Post post = createPost();
        Pageable pageable = createPageable();

        // When
        postRepository.save(post);

        // Then
        Page<Post> result = postRepository.findByIsDeletedFalse(pageable);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    private Post createPost() {
        return Post.of("Test title", "Test content");
    }

    private Pageable createPageable() {
        return PageRequest.of(0, 10, Sort.by("modifiedDate").descending());
    }

}