package com.c8n.service;

import com.c8n.model.BasicDataHub;

import java.util.Set;

public interface BasicDataCollectionService {
    boolean addCollection(String collectionName);
    boolean deleteCollection(String collectionName);
    Set<String> getAllCollectionNames();
    boolean backupCollectionByName(String collectionName);
    boolean backupAllCollections();
    boolean restoreCollectionByName(String collectionName);
    boolean restoreAllCollections();
    void initializeAllCollectionsFromDb();
    boolean truncateAllCollections();
    boolean truncateCollectionByName(String collectionName);
}
