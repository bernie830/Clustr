package com.nothing.hunnaz.clustr.EventDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.nothing.hunnaz.clustr.EventDB.EventDBSchema;

/**
 * Created by adamcchampion on 2017/08/04.
 */

public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent() {
        String title = getString(getColumnIndex(EventDBSchema.EventsTable.Cols.TITLE));
        String location = getString(getColumnIndex(EventDBSchema.EventsTable.Cols.LOCATION));
        int capacity = (int)getLong(getColumnIndex(EventDBSchema.EventsTable.Cols.CAPACITY));
        String date = getString(getColumnIndex(EventDBSchema.EventsTable.Cols.DATE));
        String description = getString(getColumnIndex(EventDBSchema.EventsTable.Cols.DESCRIPTION));
        double cost = getDouble(getColumnIndex(EventDBSchema.EventsTable.Cols.COST));
        int age = (int)getLong(getColumnIndex(EventDBSchema.EventsTable.Cols.AGE));
        String creator = getString(getColumnIndex(EventDBSchema.EventsTable.Cols.CREATOR));

        Event account = new Event(title, location, capacity, date, description, cost, age, creator, 0);

        return account;
    }
}
