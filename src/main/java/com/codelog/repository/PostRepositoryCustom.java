package com.codelog.repository;

import com.codelog.domain.Post;
import com.codelog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
