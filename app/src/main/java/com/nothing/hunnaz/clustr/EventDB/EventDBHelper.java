package com.nothing.hunnaz.clustr.EventDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public final class EventDBHelper extends SQLiteOpenHelper {
    /* All taken from TicTacToe */
    private Context mContext;
    private static final String DATABASE_NAME = "clustr_welcome_screen.db";
    private static final int DATABASE_VERSION = 1;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + EventDBSchema.EventsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventDBSchema.EventsTable.Cols.TITLE + " TEXT, " +
                EventDBSchema.EventsTable.Cols.LOCATION + " TEXT," +
                EventDBSchema.EventsTable.Cols.CAPACITY + " INTEGER, " +
                EventDBSchema.EventsTable.Cols.DATE + " TEXT," +
                EventDBSchema.EventsTable.Cols.DESCRIPTION + " TEXT, " +
                EventDBSchema.EventsTable.Cols.COST + " REAL," +
                EventDBSchema.EventsTable.Cols.AGE + " INTEGER," +
                EventDBSchema.EventsTable.Cols.CREATOR + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventDBSchema.EventsTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
