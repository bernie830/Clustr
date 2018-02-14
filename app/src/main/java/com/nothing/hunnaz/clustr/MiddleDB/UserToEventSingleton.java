package com.nothing.hunnaz.clustr.MiddleDB;

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
public class UserToEventSingleton {
    private static UserToEventSingleton mid;

    private UserToEventDBHelper userDBHelper;
    private SQLiteDatabase database;

    private static final String INSERT_STMT = "INSERT INTO " + UserToEventDBSchema.MidTable.NAME + " (name, password) VALUES (?, ?)" ;

    public static UserToEventSingleton get(Context context) {
        if (mid == null) {
            mid = new UserToEventSingleton(context);
        }
        return mid;
    }

    private UserToEventSingleton(Context context) {
        userDBHelper = new UserToEventDBHelper(context.getApplicationContext());
        database = userDBHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(UserToEvent mid) {
        ContentValues values = new ContentValues();
        values.put(UserToEventDBSchema.MidTable.Cols.USER_ID, mid.getUserID());
        values.put(UserToEventDBSchema.MidTable.Cols.EVENT_ID, mid.getEventID());

        return values;
    }

    /**
     * Add a new user Account to the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     *
     * @param mid
     */
    public void addMiddle(UserToEvent mid) {
        ContentValues contentValues = getContentValues(mid);

        database.beginTransaction();
        try {
            SQLiteStatement statement = database.compileStatement(INSERT_STMT);
            statement.bindLong(1, mid.getUserID());
            statement.bindLong(2, mid.getEventID());
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
    public void deleteAllMiddles() {
        database.beginTransaction();
        try {
            database.delete(UserToEventDBSchema.MidTable.NAME,null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private UserToEventCursorWrapper queryMiddles(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                UserToEventDBSchema.MidTable.NAME,
                null, // columns; null selects all columns
                whereClause,
                whereArgs,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return new UserToEventCursorWrapper(cursor);
    }

    public List<UserToEvent> getMiddles() {
        List<UserToEvent> accountList = new ArrayList<>();
        UserToEventCursorWrapper cursor = queryMiddles(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                accountList.add(cursor.getMid());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return accountList;
    }

}
