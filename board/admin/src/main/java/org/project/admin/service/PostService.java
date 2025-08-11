package org.project.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.admin.domain.Board;
import org.project.admin.domain.Post;
import org.project.admin.dto.PostRequestDto;
import org.project.admin.dto.PostResponseDto;
import org.project.admin.repository.BoardRepository;
import org.project.admin.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostResponseDto::from);
    }

    public PostResponseDto getPost(Long id) {
        return PostResponseDto.from(postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No post found with id: " + id)));
    }

    public Long savePost(PostRequestDto postRequestDto) {
        Board board = boardRepository.findById(postRequestDto.boardId()).orElseThrow(() -> new NoSuchElementException("No board found with id: " + postRequestDto.boardId()));
        board.addPost(Post.of(postRequestDto.title(), postRequestDto.content()));
        Post savedPost = postRepository.save(Post.of(postRequestDto.title(), postRequestDto.content()));

        return savedPost.getId();
    }

    public void updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No post found with id: " + id));

        post.setTitle(postRequestDto.title());
        post.setContent(postRequestDto.content());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No post found with id: " + id));

        if (post.getIsDeleted()) throw new IllegalStateException("Post has been deleted");

        post.setIsDeleted(true);
    }

}
