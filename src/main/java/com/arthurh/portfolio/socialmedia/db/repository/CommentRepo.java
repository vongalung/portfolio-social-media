package com.arthurh.portfolio.socialmedia.db.repository;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

import com.arthurh.portfolio.socialmedia.db.model.Comment;
import jakarta.persistence.QueryHint;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CommentRepo extends JpaRepository<Comment, UUID> {
    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    Page<Comment> findByParentId(UUID parentId, @Nullable Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.parent.id = :parentId")
    long countByParent(@Param("parentId") UUID parentId);
}
