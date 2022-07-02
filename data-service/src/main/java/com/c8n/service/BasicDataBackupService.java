package com.c8n.service;

import com.c8n.model.entity.BasicData;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BasicDataBackupService {
    BasicData saveCollection(BasicData collection);
    BasicData getCollectionById(UUID collectionId);
    BasicData updateCollection(BasicData collection);
    BasicData deleteCollection(BasicData collection);
    List<BasicData> getAllCollections();
    boolean deleteAllCollections();
}
