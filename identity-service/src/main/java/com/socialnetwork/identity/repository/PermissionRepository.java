package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {
    Optional<PermissionEntity> findByName(String name);

    @Query("SELECT p FROM permission p WHERE p.name IN :names")
    List<PermissionEntity> findAllByName(@Param("names") Iterable<String> names);
}
