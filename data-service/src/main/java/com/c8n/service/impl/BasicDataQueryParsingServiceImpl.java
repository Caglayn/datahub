package com.c8n.service.impl;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.UnrecognizedQuery;
import com.c8n.model.response.QueryResponse;
import com.c8n.service.BasicDataQueryParsingService;
import com.c8n.service.command.CommandService;
import com.c8n.service.query.QueryService;
import org.springframework.stereotype.Service;

import static com.c8n.constants.QueryConstants.*;

import java.util.*;

@Service
public class BasicDataQueryParsingServiceImpl implements BasicDataQueryParsingService {

    private final CommandService commandService;
    private final QueryService queryService;

    public BasicDataQueryParsingServiceImpl(CommandService commandService, QueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    public QueryResponse parseQuery(String query){

        if (query.startsWith(COMMAND_PREFIX)){
            boolean isCommandExecuted = parseCommand(query.substring(1));
            return QueryResponse
                    .builder()
                    .isSuccess(isCommandExecuted)
                    .operation(1)
                    .build();
        } else {
            // query
            int rowCount = 0;
            int firsSpace = query.indexOf(" ");
            if (firsSpace == -1)
                throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());

            return switch (query.substring(0, firsSpace)) {
                case SELECT -> parseSelectQuery(query);
                case UPDATE -> parseUpdateQuery(query);
                case DELETE -> parseDeleteQuery(query);
                case INSERT -> parseInsertQuery(query);
                default -> throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());
            };
        }
    }

    private boolean parseCommand(String commandString) {
        int postfixIndex = commandString.indexOf(COMMAND_SUFFIX);
        String command = commandString.substring(0, postfixIndex);
        String collectionName=null;
        String subCommand=null;
        String subType=null;

        commandString = commandString.substring(postfixIndex+1);

        if (!commandString.contains(COMMAND_PREFIX)){
            collectionName = commandString.trim();
        } else {
            int nextPrefixIndex = commandString.indexOf(COMMAND_PREFIX);
            collectionName = commandString.substring(0, nextPrefixIndex).trim();
            commandString = commandString.substring(nextPrefixIndex+1).trim();
            int nextPostFixIndex = commandString.indexOf(COMMAND_SUFFIX);

            if (nextPostFixIndex == -1)
                throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());

            subCommand = commandString.substring(0, nextPostFixIndex);
            subType = commandString.substring(nextPostFixIndex+1);
        }

        return switch (command) {
            case RESTORE -> commandService.executeRestoreCommand(collectionName);
            case BACKUP -> commandService.executeBackupCommand(collectionName);
            case TRUNCATE -> commandService.executeTruncateCommand(collectionName);
            case INDEX -> commandService.executeIndexCommand(collectionName, subCommand, subType);
            default -> throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());
        };
    }

    private QueryResponse parseInsertQuery(String query) {
        return null;
    }

    private QueryResponse parseDeleteQuery(String query) {
        return null;
    }

    private QueryResponse parseUpdateQuery(String query) {
        return null;
    }

    private QueryResponse parseSelectQuery(String query) {
        int fromIndex = query.indexOf(FROM);

        if (fromIndex == -1)
            throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());

        int whereIndex = query.indexOf(WHERE);

        Set<String> selectFields = new TreeSet<>();
        String selectString = query.substring(SELECT.length(), fromIndex);
        StringTokenizer selectTokenizer = new StringTokenizer(selectString, ",");

        while (selectTokenizer.hasMoreTokens())
            selectFields.add(selectTokenizer.nextToken().trim());

        String collectionName = whereIndex == -1 ? query.substring(fromIndex+FROM.length()).trim() : query.substring(fromIndex+FROM.length(), whereIndex).trim();

        Map<String, ArrayList<String>> whereConditions = null;
        if (whereIndex>0){
            String whereString = query.substring(whereIndex+WHERE.length()).trim();
            whereConditions = new HashMap<>();
            whereConditions.put(FIELD, new ArrayList<>());
            whereConditions.put(SYMBOL, new ArrayList<>());
            whereConditions.put(VALUE, new ArrayList<>());

            while (true){
                int firstSymbolIndex = Integer.MAX_VALUE;
                String currentSymbol = "";
                for (String SYMBOL :SYMBOL_LIST) {
                    int ix = whereString.indexOf(SYMBOL);
                    if (ix<firstSymbolIndex && ix>0){
                        firstSymbolIndex = ix;
                        currentSymbol = SYMBOL;
                    }
                }

                if (firstSymbolIndex == Integer.MAX_VALUE)
                    throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());

                String field = whereString.substring(0, firstSymbolIndex).trim();
                whereString = whereString.substring(firstSymbolIndex+1).trim();


                int nextIndex = Integer.MAX_VALUE;
                String currentPreposition = "";
                for (String PREPOSITION : PREPOSITIONS){
                    int ix = whereString.indexOf(PREPOSITION);
                    if (ix<nextIndex && ix>0){
                        nextIndex = ix;
                        currentPreposition = PREPOSITION;
                    }
                }

                if (nextIndex == Integer.MAX_VALUE)
                    throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());

                String value = whereString.substring(0, nextIndex).trim().replaceAll("'", "").replaceAll("\"", "");

                whereConditions.get(FIELD).add(field);
                whereConditions.get(SYMBOL).add(currentSymbol);
                whereConditions.get(VALUE).add(value);

                if (currentPreposition.equals(QUERY_FINISH))
                    break;

                whereString = whereString.substring(nextIndex+currentPreposition.length()).trim();
            }
        }



        return queryService.executeSelectQuery(selectFields, collectionName, whereConditions);
    }
}
