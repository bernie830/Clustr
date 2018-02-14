package com.nothing.hunnaz.clustr.UserDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public final class UserDBHelper extends SQLiteOpenHelper {
    /* All taken from TicTacToe */
    private Context mContext;
    private static final String DATABASE_NAME = "Clustr.db";
    private static final int DATABASE_VERSION = 1;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserDBSchema.AccountsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserDBSchema.AccountsTable.Cols.USERNAME + " TEXT, " +
                UserDBSchema.AccountsTable.Cols.PASSWORD + " TEXT," +
                UserDBSchema.AccountsTable.Cols.DATE_OF_BIRTH + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserDBSchema.AccountsTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
