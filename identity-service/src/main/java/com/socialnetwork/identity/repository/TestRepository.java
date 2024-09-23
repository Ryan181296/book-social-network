package com.socialnetwork.identity.repository;

import com.socialnetwork.identity.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, String> {
}
