package com.c8n.constants;

import java.util.Arrays;
import java.util.List;

public class QueryConstants {
    // query keywords
    public static final String SELECT = "select";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String INSERT = "insert";
    public static final String FROM = "from";
    public static final String WHERE = "where";
    public static final String AND = "and";
    public static final String OR = "or";

    // commands
    public static final String COMMAND_PREFIX = "#";
    public static final String COMMAND_SUFFIX = ":";
    public static final String ALL = "all";
    public static final String RESTORE = "restore";
    public static final String BACKUP = "backup";
    public static final String TRUNCATE = "truncate";
    public static final String INDEX = "index";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";

    // symbols

    public static final String GREATER = ">";
    public static final String LESS = "<";
    public static final String EQUAL = "=";
    public static final String QUERY_FINISH = ";";

    // lists
    public static final List<String> SYMBOL_LIST = Arrays.asList(GREATER, LESS, EQUAL);
    public static final List<String> PREPOSITIONS = Arrays.asList(AND, OR, QUERY_FINISH);

    // transfer parameters
    public static final String FIELD = "field";
    public static final String SYMBOL = "symbol";
    public static final String VALUE = "value";

}
