package com.nothing.hunnaz.clustr.UserDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class UserSingleton {
    private static UserSingleton user;

    private UserDBHelper userDBHelper;
    private SQLiteDatabase database;

    private static final String INSERT_STMT = "INSERT INTO " + UserDBSchema.AccountsTable.NAME + " (name, password, birth_date) VALUES (?, ?, ?)" ;

    public static UserSingleton get(Context context) {
        if (user == null) {
            user = new UserSingleton(context);
        }
        return user;
    }

    private UserSingleton(Context context) {
        userDBHelper = new UserDBHelper(context.getApplicationContext());
        database = userDBHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(User account) {
        ContentValues values = new ContentValues();
        values.put(UserDBSchema.AccountsTable.Cols.USERNAME, account.getName());
        values.put(UserDBSchema.AccountsTable.Cols.PASSWORD, account.getPassword());
        values.put(UserDBSchema.AccountsTable.Cols.DATE_OF_BIRTH, account.getDateOfBirth());

        return values;
    }

    /**
     * Add a new user Account to the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     *
     * @param account
     */
    public void addAccount(User account) {
        ContentValues contentValues = getContentValues(account);

        database.beginTransaction();
        try {
            SQLiteStatement statement = database.compileStatement(INSERT_STMT);
            statement.bindString(1, account.getName());
            statement.bindString(2, account.getPassword());
            statement.bindString(3, account.getDateOfBirth());
            statement.executeInsert();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    /**
     * Delete all user accounts from the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     */
    public void deleteAllAccounts() {
        database.beginTransaction();
        try {
            database.delete(UserDBSchema.AccountsTable.NAME,null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private UserCursorWrapper queryAccounts(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                UserDBSchema.AccountsTable.NAME,
                null, // columns; null selects all columns
                whereClause,
                whereArgs,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return new UserCursorWrapper(cursor);
    }

    private List<User> getAllAccounts(){
        List<User> accountList = new ArrayList<>();
        UserCursorWrapper cursor = queryAccounts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                accountList.add(cursor.getAccount());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return accountList;
    }

    public boolean isRegistered(String username){
        List<User> accounts = getAllAccounts();
        boolean returnVal = false;
        for(User u : accounts){
            if(u.getName().equals(username)){
                returnVal = true;
            }
        }
        return returnVal;
    }

    public List<User> getAccounts() {
        return getAllAccounts();
    }
}
