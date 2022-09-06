package com.codelog.service;

import com.codelog.domain.Post;
import com.codelog.repository.PostRepository;
import com.codelog.request.PostCreate;
import com.codelog.request.PostEdit;
import com.codelog.request.PostSearch;
import com.codelog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate
                .builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Long postId = 1L;

        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);


        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("foo", response.getTitle());
        Assertions.assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("foo" + i)
                        .content("bar1" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();


        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("foo19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("반포자이")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("호돌걸", changePost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("호돌맨")
                .content("초가집")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다. id = " + post.getId()));
        Assertions.assertEquals("초가집", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        Assertions.assertEquals(0L, postRepository.count());
    }
}