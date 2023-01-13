package com.sparta.boardprac.post.entity;

import com.sparta.boardprac.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column
    private String username;

    @Builder
    public Post(final String title, final String content, final String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
