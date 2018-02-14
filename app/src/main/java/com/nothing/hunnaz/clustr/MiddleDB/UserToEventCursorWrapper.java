package com.nothing.hunnaz.clustr.MiddleDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.nothing.hunnaz.clustr.MiddleDB.UserToEventDBSchema;

/**
 * Created by adamcchampion on 2017/08/04.
 */

public class UserToEventCursorWrapper extends CursorWrapper {
    public UserToEventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public UserToEvent getMid() {
        int user_id = (int)getLong(getColumnIndex(UserToEventDBSchema.MidTable.Cols.USER_ID));
        int event_id = (int)getLong(getColumnIndex(UserToEventDBSchema.MidTable.Cols.EVENT_ID));

        UserToEvent mid = new UserToEvent(user_id, event_id);

        return mid;
    }
}
