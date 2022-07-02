package com.c8n.service.impl;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.CollectionNotFoundException;
import com.c8n.model.entity.BasicData;
import com.c8n.repository.BasicDataRepository;
import com.c8n.service.BasicDataBackupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BasicDataBackupServiceImpl implements BasicDataBackupService {
    private final BasicDataRepository basicDataRepository;

    public BasicDataBackupServiceImpl(BasicDataRepository basicDataRepository) {
        this.basicDataRepository = basicDataRepository;
    }

    @Override
    public BasicData saveCollection(BasicData collection) {
        return basicDataRepository.save(collection);
    }

    @Override
    public BasicData getCollectionById(UUID collectionId) {
        return basicDataRepository.findById(collectionId).orElse(null);
    }

    @Override
    public BasicData updateCollection(BasicData collection) {
        return basicDataRepository.save(collection);
    }

    @Override
    public BasicData deleteCollection(BasicData collection) {
        basicDataRepository.deleteById(collection.getId());
        return collection;
    }

    @Override
    public List<BasicData> getAllCollections() {
        return basicDataRepository.findAll();
    }

    @Override
    public boolean deleteAllCollections() {
        basicDataRepository.deleteAll();
        return true;
    }
}
