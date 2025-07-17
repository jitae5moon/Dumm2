package org.project.admin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.admin.domain.Post;
import org.project.admin.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    MockMvc mockMvc;
    PostRepository postRepository;

    @Autowired
    public PostControllerTest(MockMvc mockMvc, PostRepository postRepository) {
        this.mockMvc = mockMvc;
        this.postRepository = postRepository;
    }

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/admin/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/list"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void getPost() throws Exception {
        // Given & When
        Post post = createPost();
        Post savedPost = postRepository.save(post);

        // Then
        mockMvc.perform(get("/admin/posts/" + savedPost.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/detail"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void viewSaveForm() throws Exception {
        mockMvc.perform(get("/admin/posts/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"));
    }

    @Test
    void testSavePost() throws Exception {
        mockMvc.perform(post("/admin/posts/save")
                .param("title", "Test title")
                .param("content", "Test content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/posts"));
    }

    @Test
    void givenEmptyParamsWhenSavingPostThenReturnsSaveFormPage() throws Exception {
        mockMvc.perform(post("/admin/posts/save")
                .param("title", "")
                .param("content", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"))
                .andExpect(model().attributeHasFieldErrors("postRequestDto", "title", "content"));
    }

    @Test
    void viewUpdateForm() throws Exception {
        // Given && When
        Post post = createPost();
        Post savedPost = postRepository.save(post);

        // Then
        mockMvc.perform(get("/admin/posts/" + savedPost.getId() + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void testUpdatePost() throws Exception {
        // Given && When
        Post post = createPost();
        Post savedPost = postRepository.save(post);

        // Then
        mockMvc.perform(post("/admin/posts/" + post.getId() + "/update")
                .param("title", "Updated test title")
                .param("content", "Updated test content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/posts/" +  savedPost.getId()));
    }

    @Test
    void givenSavedPostWhenUpdatingPostWithEmptyParamsThenReturnsUpdateFormPage() throws Exception {
        // Given && When
        Post savedPost = postRepository.save(createPost());

        // Then
        mockMvc.perform(post("/admin/posts/" + savedPost.getId() + "/update")
                .param("title", "")
                .param("content", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/form"))
                .andExpect(model().attributeHasFieldErrors("postRequestDto", "title", "content"));

    }

    private Post createPost() {
        return Post.of("Test title", "Test content");
    }

}