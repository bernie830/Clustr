package com.nothing.hunnaz.clustr.EventDB;

/**
 * Created by hunterbernhardt on 2/9/18.
 */

public class EventDBSchema {
    public static final class EventsTable {
        /* All taken from TicTacToe then edited */
        public static final String NAME = "events";

        public static final class Cols {
            public static final String TITLE = "title";
            public static final String LOCATION = "location";
            public static final String CAPACITY = "capacity";
            public static final String DATE = "date";
            public static final String DESCRIPTION = "description";
            public static final String COST = "cost";
            public static final String AGE = "age";
            public static final String CREATOR = "creator";

            // public static final String IMAGE = "password";
        }
    }
}
