package com.example.hunterbernhardt.myapplication;

/**
 * Created by hunterbernhardt on 2/9/18.
 */

public class UserDBSchema {
    public static final class AccountsTable {
        /* All taken from TicTacToe then edited */
        public static final String NAME = "accounts";

        public static final class Cols {
            public static final String USERNAME = "name";
            public static final String PASSWORD = "password";
            public static final String DATE_OF_BIRTH = "birth_date";
        }
    }
}
