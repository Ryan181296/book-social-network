package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
