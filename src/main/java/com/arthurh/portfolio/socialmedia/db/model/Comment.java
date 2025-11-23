package com.arthurh.portfolio.socialmedia.db.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DiscriminatorValue(UserContent.ContentType.COMMENT)
public class Comment extends UserContent {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Valid
    private UserContent parent;
}
