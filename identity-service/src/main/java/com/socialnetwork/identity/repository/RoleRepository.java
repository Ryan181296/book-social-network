package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(String name);

    @Query("SELECT r FROM role r WHERE r.name IN :names")
    List<RoleEntity> findAllByName(@Param("names") Iterable<String> names);
}
