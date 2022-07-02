package com.c8n.service.command;

public interface CommandService {
    boolean executeRestoreCommand(String collectionName);
    boolean executeBackupCommand(String collectionName);
    boolean executeTruncateCommand(String collectionName);
    boolean executeIndexCommand(String collectionName, String subCommand, String subType);
}
