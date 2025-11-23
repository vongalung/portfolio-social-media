package com.arthurh.portfolio.socialmedia.db.repository;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.arthurh.portfolio.socialmedia.db.model.UserContent;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserContentRepo extends JpaRepository<UserContent, UUID> {
    @Lock(PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("SELECT uc FROM UserContent uc WHERE uc.id = :id")
    Optional<UserContent> findByIdForUpdate(@Param("id") UUID id);
}
