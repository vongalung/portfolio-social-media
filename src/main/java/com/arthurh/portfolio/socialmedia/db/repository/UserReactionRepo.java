package com.arthurh.portfolio.socialmedia.db.repository;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

import com.arthurh.portfolio.socialmedia.db.model.UserReaction;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface UserReactionRepo extends JpaRepository<UserReaction, UUID> {
    Optional<UserReaction> findByContent_IdAndUser_Id(UUID contentId, UUID userId);

    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    @Query("SELECT ur FROM UserReaction ur WHERE ur.content,id = :contentId")
    Stream<UserReaction> streamByContent(@Param("contentId") UUID contentId);
}
