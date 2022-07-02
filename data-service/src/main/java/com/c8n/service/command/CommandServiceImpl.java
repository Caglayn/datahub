package com.c8n.service.command;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.CollectionNotFoundException;
import com.c8n.model.BasicDataHub;
import com.c8n.service.BasicDataCollectionService;
import com.c8n.service.BasicDataIndexService;
import com.c8n.util.HubUtil;
import org.springframework.stereotype.Service;

import static com.c8n.constants.QueryConstants.*;

@Service
public class CommandServiceImpl implements CommandService{

    private final BasicDataHub basicDataHub;
    private final BasicDataCollectionService collectionService;

    private final BasicDataIndexService indexService;

    public CommandServiceImpl(BasicDataHub basicDataHub, BasicDataCollectionService collectionService, BasicDataIndexService indexService) {
        this.basicDataHub = basicDataHub;
        this.collectionService = collectionService;
        this.indexService = indexService;
    }

    @Override
    public boolean executeRestoreCommand(String collectionName) {
        if (!basicDataHub.getHub().containsKey(collectionName) && !collectionName.equals(ALL))
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);

        if (collectionName.equals(ALL))
            return collectionService.restoreAllCollections();
        else
            return collectionService.restoreCollectionByName(collectionName);
    }

    @Override
    public boolean executeBackupCommand(String collectionName) {
        if (!basicDataHub.getHub().containsKey(collectionName) && !collectionName.equals(ALL))
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);

        if (collectionName.equals(ALL))
            return collectionService.backupAllCollections();
        else
            return collectionService.backupCollectionByName(collectionName);
    }

    @Override
    public boolean executeTruncateCommand(String collectionName) {
        if (!basicDataHub.getHub().containsKey(collectionName) && !collectionName.equals(ALL))
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);

        if (collectionName.equals(ALL))
            return collectionService.truncateAllCollections();
        else
            return collectionService.truncateCollectionByName(collectionName);
    }

    @Override
    public boolean executeIndexCommand(String collectionName, String subCommand, String subType) {
        if (!basicDataHub.getHub().containsKey(collectionName) && !collectionName.equals(ALL))
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName);

        if (collectionName.equalsIgnoreCase(ALL)){
            return indexService.updateAllIndexes();
        } else {
            if (subType.equalsIgnoreCase(ALL)){
                if (subCommand.equalsIgnoreCase(UPDATE)){
                    return indexService.updateIndexesByCollectionName(collectionName.toLowerCase());
                } else
                    return false;
            } else {
                return switch (subCommand.toLowerCase()){
                    case UPDATE -> indexService.updateIndexByCollectionAndColumn(collectionName, subType);
                    case REMOVE -> indexService.deleteIndexFromCollection(collectionName, subType);
                    case ADD -> indexService.addIndexToCollection(collectionName, subType);
                    default -> false;
                };
            }
        }
    }
}
