package com.sparta.boardprac.like.entity;

import com.sparta.boardprac.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userID", "commentId"})
        }
)
public class CommentLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long commentId;

    @Builder
    public CommentLike(final Long userId, final Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

}
