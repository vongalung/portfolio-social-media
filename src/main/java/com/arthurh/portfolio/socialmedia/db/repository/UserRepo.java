package com.arthurh.portfolio.socialmedia.db.repository;

import static jakarta.persistence.LockModeType.PESSIMISTIC_READ;

import com.arthurh.portfolio.socialmedia.db.model.User;
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
public interface UserRepo extends JpaRepository<User, UUID> {
    @Lock(PESSIMISTIC_READ)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("SELECT u FROM User u WHERE u.uniqueId = :uniqueId")
    Optional<User> findByUniqueIdForUpdate(@Param("uniqueId") String uniqueId);
}
