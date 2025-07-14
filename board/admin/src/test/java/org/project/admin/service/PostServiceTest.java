package org.project.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.admin.dto.PostRequestDto;
import org.project.admin.dto.PostResponseDto;
import org.project.admin.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @DisplayName("Test getPosts()")
    @Test
    void givenMultiplePostsWhenGettingAllPostsThenReturnsPageList() {
        // Given
        for (int i = 0; i < 35; i++) {
            postService.savePost(new PostRequestDto("Test title " + i, "Test content " + i, false));
        }
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modifiedDate").descending());

        // When
        Page<PostResponseDto> posts = postService.getPosts(pageable);

        // Then
        assertThat(posts).isNotNull();
        assertThat(posts.getTotalElements()).isEqualTo(35);
        assertThat(posts.getTotalPages()).isEqualTo(4);
    }

    @DisplayName("Test getPost()")
    @Test
    void givenPostIdWhenGettingPostByIdThenReturnsCorrespondingPostResponseDto
            () {
        // Given
        PostRequestDto requestDto = createPostRequestDto();
        String title = requestDto.title();
        String content = requestDto.content();
        Long id = postService.savePost(requestDto);

        // When
        PostResponseDto responseDto = postService.getPost(id);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.title()).isEqualTo(title);
        assertThat(responseDto.content()).isEqualTo(content);
    }

    @DisplayName("Test savePost()")
    @Test
    void givenPostRequestDtoWhenSavingPostThenReturnsPostId() {
        // Given
        PostRequestDto requestDto = createPostRequestDto();

        // When
        Long id = postService.savePost(requestDto);

        // Then
        assertThat(id).isNotNull();
        assertThat(postRepository.findById(id)).isPresent();
    }

    @DisplayName("Test updatePost()")
    @Test
    void givenUpdatedPostTitleWhenUpdatingPostThenUpdatesAndReturnsNothing() {
        // Given
        PostRequestDto requestDto = createPostRequestDto();
        Long id = postService.savePost(requestDto);
        PostRequestDto updateRequestDto = new PostRequestDto("Updated test title", "Updated test content", false);

        // When
        postService.updatePost(id, updateRequestDto);
        PostResponseDto responseDto = postService.getPost(id);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.title()).isEqualTo(updateRequestDto.title());
        assertThat(responseDto.content()).isEqualTo(updateRequestDto.content());
    }

    @DisplayName("Test deletePost()")
    @Test
    void givenPostIdWhenDeletingPostThenDeletesAndReturnsNothing() {
        // Given
        Long id = postService.savePost(createPostRequestDto());

        // When
        postService.deletePost(id);

        // Then
        assertThat(postRepository.findById(id).get().getIsDeleted()).isTrue();
    }

    private PostRequestDto createPostRequestDto() {
        return new PostRequestDto("Test title",
                "Test content",
                false);
    }

}