package org.project.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.admin.dto.PostRequestDto;
import org.project.admin.dto.PostResponseDto;
import org.project.admin.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String saveForm(Model model) {
        log.info("PostController :: saveForm()");

        model.addAttribute("post", new PostResponseDto(null, "", "", false, 0, 0, null, null));

        return "posts/form";
    }

    @PostMapping("/save")
    public String savePost(@Valid PostRequestDto postRequestDto, BindingResult bindingResult) {
        log.info("PostController :: savePost() :: postRequestDto = {}", postRequestDto);

        if (bindingResult.hasErrors()) return "posts/form";

        postService.savePost(postRequestDto);

        return "redirect:/admin/posts";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        log.info("PostController :: updateForm() :: id = {}", id);

        PostResponseDto post = postService.getPost(id);
        model.addAttribute("post", post);

        return "posts/form";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, @Valid PostRequestDto postRequestDto, BindingResult bindingResult, Model model) {
        log.info("PostController :: updatePost() :: id = {}", id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postRequestDto);

            return "posts/form";
        }

        postService.updatePost(id, postRequestDto);

        return "redirect:/admin/posts/" + id;
    }

}
