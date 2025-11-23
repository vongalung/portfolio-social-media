package com.arthurh.portfolio.socialmedia.db.model;

import org.jspecify.annotations.NullMarked;
import java.time.ZonedDateTime;

@NullMarked
public interface UserInteraction {
    ZonedDateTime getCreatedAt();
    User getUser();
}
