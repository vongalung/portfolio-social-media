package com.arthurh.portfolio.socialmedia.db.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends BaseData  {
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @NotBlank
    @Column(unique = true)
    private String uniqueId;
    @NotBlank
    private String username;

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private List<@Valid UserContent> contents = new ArrayList<>();
}
