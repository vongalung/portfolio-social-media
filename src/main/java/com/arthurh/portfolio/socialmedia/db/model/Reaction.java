package com.arthurh.portfolio.socialmedia.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Reaction extends BaseData {
    @CreationTimestamp
    private ZonedDateTime createdAt;
    private ZonedDateTime inactiveAt;

    @Column(unique = true)
    @NotBlank
    private String name;
}
