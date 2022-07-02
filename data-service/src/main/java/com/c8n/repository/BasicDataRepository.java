package com.c8n.repository;

import com.c8n.model.entity.BasicData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BasicDataRepository extends MongoRepository<BasicData, UUID> {
}
