package org.project.admin.repository;

import org.junit.jupiter.api.Test;
import org.project.admin.config.JpaConfig;
import org.project.admin.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void createPost() {
        // Given
        Post post = Post.of("Test title", "Test content");

        // When
        postRepository.save(post);

        // Then
        assertThat(postRepository.findAll()).hasSize(1);
        assertThat(postRepository.findById(1L).get().getCreatedBy()).isEqualTo("TEST_ADMIN");
    }

}