package com.nothing.hunnaz.clustr.MiddleDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public final class UserToEventDBHelper extends SQLiteOpenHelper {
    /* All taken from TicTacToe */
    private Context mContext;
    private static final String DATABASE_NAME = "clustr_welcome_screen.db";
    private static final int DATABASE_VERSION = 1;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public UserToEventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        
        sqLiteDatabase.execSQL("CREATE TABLE " + UserToEventDBSchema.MidTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserToEventDBSchema.MidTable.Cols.USER_ID + " INTEGER, " +
                UserToEventDBSchema.MidTable.Cols.EVENT_ID + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserToEventDBSchema.MidTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
