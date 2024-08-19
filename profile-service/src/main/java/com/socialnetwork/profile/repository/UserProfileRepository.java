package com.socialnetwork.profile.repository;

import com.socialnetwork.profile.entity.UserProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfileEntity, String> {
}
