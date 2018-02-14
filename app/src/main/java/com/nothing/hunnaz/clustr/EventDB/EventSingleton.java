package com.nothing.hunnaz.clustr.EventDB;

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
public class EventSingleton {
    private static EventSingleton user;

    private EventDBHelper userDBHelper;
    private SQLiteDatabase database;

    private static final String INSERT_STMT = "INSERT INTO " + EventDBSchema.EventsTable.NAME + " (name, password, birth_date) VALUES (?, ?, ?)" ;

    public static EventSingleton get(Context context) {
        if (user == null) {
            user = new EventSingleton(context);
        }
        return user;
    }

    private EventSingleton(Context context) {
        userDBHelper = new EventDBHelper(context.getApplicationContext());
        database = userDBHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();

        values.put(EventDBSchema.EventsTable.Cols.TITLE, event.getTitle());
        values.put(EventDBSchema.EventsTable.Cols.LOCATION, event.getLocation());
        values.put(EventDBSchema.EventsTable.Cols.CAPACITY, event.getCapacity());
        values.put(EventDBSchema.EventsTable.Cols.DATE, event.getDate());
        values.put(EventDBSchema.EventsTable.Cols.DESCRIPTION, event.getDescription());
        values.put(EventDBSchema.EventsTable.Cols.COST, event.getCost());
        values.put(EventDBSchema.EventsTable.Cols.AGE, event.getAge());
        values.put(EventDBSchema.EventsTable.Cols.CREATOR, event.getCreator());

        return values;
    }

    /**
     * Add a new user Account to the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     *
     * @param event
     */
    public void addEvent(Event event) {
        ContentValues contentValues = getContentValues(event);

        database.beginTransaction();
        try {
            SQLiteStatement statement = database.compileStatement(INSERT_STMT);
            statement.bindString(1, event.getTitle());
            statement.bindString(2, event.getLocation());
            statement.bindLong(3, event.getCapacity());
            statement.bindString(4, event.getDate());
            statement.bindString(5, event.getDescription());
            statement.bindDouble(6, event.getCost());
            statement.bindLong(7, event.getAge());
            statement.bindString(8, event.getCreator());

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
    public void deleteAllEvents() {
        database.beginTransaction();
        try {
            database.delete(EventDBSchema.EventsTable.NAME,null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                EventDBSchema.EventsTable.NAME,
                null, // columns; null selects all columns
                whereClause,
                whereArgs,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return new EventCursorWrapper(cursor);
    }

    public List<Event> getEvents() {
        List<Event> eventList = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                eventList.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return eventList;
    }

}
