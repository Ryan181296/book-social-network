package com.socialnetwork.post.repository;

import com.socialnetwork.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    Page<PostEntity> findAllUserByUserId(String userId, Pageable pageable);
}
