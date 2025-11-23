package com.arthurh.portfolio.socialmedia.db.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class UserContent extends BaseData implements UserInteraction {
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "parent", orphanRemoval = true)
    @ToString.Exclude
    private List<@Valid Comment> replies = new ArrayList<>();

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "content", orphanRemoval = true)
    @ToString.Exclude
    private List<@Valid UserReaction> reactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Valid
    @NotNull
    private User user;

    public void addReaction(UserReaction reaction) {
        reaction.setContent(this);
        reactions.add(reaction);
    }

    public void removeReaction(UserReaction reaction) {
        reaction.setContent(null);
        reactions.remove(reaction);
    }

    public final static class ContentType {
        public static final String POST = "POST";
        public static final String COMMENT = "COMMENT";
    }
}
