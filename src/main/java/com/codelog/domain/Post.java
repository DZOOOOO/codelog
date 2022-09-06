package com.codelog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 서비스의 정책을 엔티티에는 절대로 넣지 말자..!!!!!!!!!
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // 크기가 varChar(255)를 넘길 수 도 있기때문
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }

}
