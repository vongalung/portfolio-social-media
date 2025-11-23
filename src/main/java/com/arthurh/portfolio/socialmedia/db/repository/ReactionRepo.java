package com.arthurh.portfolio.socialmedia.db.repository;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

import com.arthurh.portfolio.socialmedia.db.model.Reaction;
import jakarta.persistence.QueryHint;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepo extends JpaRepository<Reaction, UUID> {
    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    Page<Reaction> findAllByInactiveAtIsNull(@Nullable Pageable pageable);

    Optional<Reaction> findByName(@NotBlank String name);
}
