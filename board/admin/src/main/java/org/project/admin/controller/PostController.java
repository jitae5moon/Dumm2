package org.project.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.admin.dto.PostRequestDto;
import org.project.admin.dto.PostResponseDto;
import org.project.admin.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping
    public String listPosts(Pageable pageable, Model model) {
        log.info("PostController :: listPosts() :: pageable = {}", pageable);

        Page<PostResponseDto> posts = postService.getPosts(pageable);
        model.addAttribute("posts", posts);

        return "posts/list";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        log.info("PostController :: viewPost() :: id = {}", id);

        PostResponseDto post = postService.getPost(id);
        model.addAttribute("post", post);

        return "posts/detail";
    }

    @GetMapping("/save")
    public String saveForm() {
        log.info("PostController :: saveForm()");

        return "posts/form";
    }

    @PostMapping("/save")
    public String savePost(PostRequestDto postRequestDto) {
        log.info("PostController :: savePost() :: postRequestDto = {}", postRequestDto);

        postService.savePost(postRequestDto);

        return "redirect:/admin/posts";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        log.info("PostController :: updateForm() :: id = {}", id);

        PostResponseDto post = postService.getPost(id);
        model.addAttribute("post", post);

        return "posts/form";
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable Long id, PostRequestDto postRequestDto) {
        log.info("PostController :: updatePost() :: id = {}", id);

        postService.updatePost(id, postRequestDto);

        return "redirect:/admin/posts/" + id;
    }

}
