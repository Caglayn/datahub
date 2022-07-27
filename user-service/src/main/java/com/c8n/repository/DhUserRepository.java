package com.c8n.repository;

import com.c8n.model.entity.DhUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DhUserRepository extends MongoRepository<DhUser, String> {
}
