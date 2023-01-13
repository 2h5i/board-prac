package com.sparta.boardprac.comment.entity;

import com.sparta.boardprac.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    @Column
    private Long parentId;

    @Builder
    public Comment(final Long postId, final Long userId, final String content, Long parentId) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.parentId = parentId;
    }

    public void update(final String content) {
        this.content = content;
    }
}
