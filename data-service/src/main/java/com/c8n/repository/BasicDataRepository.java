package com.c8n.repository;

import com.c8n.model.entity.BasicData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasicDataRepository extends MongoRepository<BasicData, UUID> {
}
