package com.example.hunterbernhardt.myapplication;

/**
 * Created by hunterbernhardt on 2/9/18.
 */

public class UserToEventDBSchema {
    public static final class AccountsTable {
        /* All taken from TicTacToe then edited */
        public static final String NAME = "middle";

        public static final class Cols {
            public static final String USER_ID = "user_id";
            public static final String EVENT_ID = "event_id";
        }
    }
}
