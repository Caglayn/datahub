package com.c8n.service.impl;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.CollectionNameAlreadyInUse;
import com.c8n.exception.CollectionNotFoundException;
import com.c8n.model.BasicDataHub;
import com.c8n.model.entity.BasicData;
import com.c8n.service.BasicDataBackupService;
import com.c8n.service.BasicDataCollectionService;
import com.c8n.service.BasicDataIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
public class BasicDataCollectionServiceImpl implements BasicDataCollectionService {
    private final BasicDataHub basicDataHub;
    private final BasicDataBackupService basicDataBackupService;
    private final BasicDataIndexService basicDataIndexService;

    public BasicDataCollectionServiceImpl(BasicDataHub basicDataHub, BasicDataBackupService basicDataBackupService, BasicDataIndexService basicDataIndexService) {
        this.basicDataHub = basicDataHub;
        this.basicDataBackupService = basicDataBackupService;
        this.basicDataIndexService = basicDataIndexService;
    }

    @Override
    public boolean addCollection(String collectionName) {
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) != null)
            throw new CollectionNameAlreadyInUse(CollectionStatus.NAME_IN_USE.getCode(), collectionName.toLowerCase());

        basicDataHub.getHub().put(collectionName.toLowerCase(),
                BasicData.builder()
                        .id(UUID.randomUUID())
                        .createdDate(System.currentTimeMillis())
                        .collectionName(collectionName.toLowerCase())
                        .columns(new HashSet<>())
                        .collection(new TreeMap<>(String.CASE_INSENSITIVE_ORDER))
                        .indexedColumns(new HashSet<>())
                        .build());

        return true;
    }

    @Override
    public boolean deleteCollection(String collectionName) {
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) == null)
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName.toLowerCase());

        basicDataHub.getHub().remove(collectionName.toLowerCase());
        basicDataIndexService.removeCollection(collectionName.toLowerCase());
        return true;
    }

    @Override
    public Set<String> getAllCollectionNames() {
        return basicDataHub.getHub().keySet();
    }

    @Override
    public boolean backupCollectionByName(String collectionName) {
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) == null)
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName.toLowerCase());

        log.info("Starting the backup...");
        basicDataBackupService.saveCollection(basicDataHub.getHub().get(collectionName.toLowerCase()));
        log.info("Backup finished.");
        return false;
    }

    @Override
    public boolean backupAllCollections() {
        log.info("Starting the backup...");
        basicDataBackupService.deleteAllCollections();
        basicDataHub.getHub().values().forEach(basicDataBackupService::saveCollection);
        log.info("Backup finished.");
        return true;
    }

    @Override
    public boolean restoreCollectionByName(String collectionName) {
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) == null)
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);

        log.info("Starting the restore...");
        UUID collectionId = basicDataHub.getHub().get(collectionName.toLowerCase()).getId();
        BasicData restoredCollection = basicDataBackupService.getCollectionById(collectionId);
        if (restoredCollection != null) {
            basicDataHub.getHub().put(collectionName.toLowerCase(), restoredCollection);
            log.info("Restore finished.");
            basicDataIndexService.truncateIndexesByCollectionName(collectionName.toLowerCase());
            basicDataIndexService.updateIndexesByCollectionName(collectionName.toLowerCase());
            return true;
        } else {
            log.error("Restore could not be completed, collection not found in backup database, collectionName : " + collectionName.toLowerCase());
            return false;
        }
    }

    @Override
    public boolean restoreAllCollections() {
        log.info("Starting the restore...");
        List<BasicData> collectionList = basicDataBackupService.getAllCollections();

        if (collectionList == null || collectionList.size()==0)
            return false;

        basicDataHub.getHub().clear();
        collectionList.forEach(basicData -> basicDataHub.getHub().put(basicData.getCollectionName().toLowerCase(), basicData));
        log.info("Restore finished.");
        basicDataIndexService.clearAllIndexes();
        basicDataIndexService.initializeAllIndexesOnStartUp();

        return true;
    }

    @Override
    @PostConstruct
    public void initializeAllCollectionsFromDb() {
        log.info("Starting the restore...");
        List<BasicData> collectionList = basicDataBackupService.getAllCollections();

        collectionList.forEach(basicData -> basicDataHub.getHub().put(basicData.getCollectionName().toLowerCase(), basicData));
        log.info("Restore finished.");
        basicDataIndexService.initializeAllIndexesOnStartUp();
    }

    @Override
    public boolean truncateAllCollections() {
        log.info("Starting the truncate for all collections.");
        for (String collectionName : basicDataHub.getHub().keySet()){
            basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().clear();
            basicDataIndexService.truncateIndexesByCollectionName(collectionName.toLowerCase()); // Truncate index table
        }
        log.info("All collections truncated.");
        return true;
    }

    @Override
    public boolean truncateCollectionByName(String collectionName) {
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) == null)
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);
        log.info("Starting the truncate for collection : " + collectionName.toLowerCase());
        basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().clear();
        basicDataIndexService.truncateIndexesByCollectionName(collectionName.toLowerCase());
        log.info(collectionName.toLowerCase() + " truncated.");
        return true;
    }
}
