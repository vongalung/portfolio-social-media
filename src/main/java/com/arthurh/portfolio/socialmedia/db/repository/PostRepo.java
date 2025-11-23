package com.arthurh.portfolio.socialmedia.db.repository;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

import com.arthurh.portfolio.socialmedia.db.model.Post;
import jakarta.persistence.QueryHint;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {
    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    Page<Post> findAll(@Nullable Pageable pageable);

    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    Page<Post> findByUser_Id(UUID userId, @Nullable Pageable pageable);
}
