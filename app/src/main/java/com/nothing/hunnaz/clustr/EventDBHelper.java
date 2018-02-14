package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nothing.hunnaz.clustr.EventDBSchema.AccountsTable;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public final class EventDBHelper extends SQLiteOpenHelper {
    /* All taken from TicTacToe */
    private Context mContext;
    private static final String DATABASE_NAME = "Clustr.db";
    private static final int DATABASE_VERSION = 1;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + AccountsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccountsTable.Cols.TITLE + " TEXT, " +
                AccountsTable.Cols.LOCATION + " TEXT," +
                AccountsTable.Cols.CAPACITY + " INTEGER, " +
                AccountsTable.Cols.DATE + " TEXT," +
                AccountsTable.Cols.DESCRIPTION + " TEXT, " +
                AccountsTable.Cols.COST + " REAL," +
                AccountsTable.Cols.AGE + " INTEGER," +
                AccountsTable.Cols.CREATOR + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountsTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
