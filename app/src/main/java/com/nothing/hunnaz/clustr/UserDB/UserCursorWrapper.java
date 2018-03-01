package com.nothing.hunnaz.clustr.UserDB;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by adamcchampion on 2017/08/04.
 */

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getAccount() {
        String name = getString(getColumnIndex(UserDBSchema.AccountsTable.Cols.USERNAME));
        String password = getString(getColumnIndex(UserDBSchema.AccountsTable.Cols.PASSWORD));
        String dob = getString(getColumnIndex(UserDBSchema.AccountsTable.Cols.DATE_OF_BIRTH));

        User account = new User(dob, "email@email.com", password, name);


        return account;
    }
}
