package com.nothing.hunnaz.clustr;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.nothing.hunnaz.clustr.UserDBSchema.AccountsTable;

/**
 * Created by adamcchampion on 2017/08/04.
 */

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getAccount() {
        String name = getString(getColumnIndex(AccountsTable.Cols.USERNAME));
        String password = getString(getColumnIndex(AccountsTable.Cols.PASSWORD));
        String dob = getString(getColumnIndex(AccountsTable.Cols.DATE_OF_BIRTH));

        User account = new User(name, password, dob);

        return account;
    }
}
