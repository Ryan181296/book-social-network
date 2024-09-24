package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, String> {
    @Query(value = "SELECT * FROM test t " +
            "WHERE (:address IS NULL OR t.address LIKE CONCAT('%', :address, '%')) " +
            "AND (:description IS NULL OR t.description LIKE CONCAT('%', :description, '%'))",
            nativeQuery = true)
    Page<TestEntity> findAll(@Param("address") String address,
                             @Param("description") String description, Pageable pageable);
}
