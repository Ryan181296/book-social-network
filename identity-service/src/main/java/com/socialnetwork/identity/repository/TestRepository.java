package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.DiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<DiscountEntity, String> {
    @Query(value = "SELECT * FROM discount d WHERE d.create_date BETWEEN '2023-12-01' AND '2024-01-30'",
            nativeQuery = true)
    Page<DiscountEntity> findAll(@Param("address") String address,
                                 @Param("description") String description, Pageable pageable);
}