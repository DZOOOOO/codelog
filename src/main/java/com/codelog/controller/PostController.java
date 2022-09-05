package com.codelog.controller;

import com.codelog.request.PostCreate;
import com.codelog.request.PostSearch;
import com.codelog.response.PostResponse;
import com.codelog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    /*
        /posts -> 글 전체 조회(검색 + 페이징)
        /posts/{postsId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postsId}")
    public PostResponse get(@PathVariable(name = "postsId") Long postsId) {
        PostResponse response = postService.get(postsId);
        return response;
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }



}
