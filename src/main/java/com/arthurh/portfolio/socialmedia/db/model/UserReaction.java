package com.arthurh.portfolio.socialmedia.db.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.ZonedDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "one_reaction_per_user_per_content", columnNames = {"user", "content"})})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserReaction extends BaseData implements UserInteraction {
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "reaction_id")
    @Valid
    @NotNull
    private Reaction reaction;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @Valid
    @NotNull
    private UserContent content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Valid
    @NotNull
    private User user;
}
