package com.soszik.myplaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Osaka on 18.02.2017.
 * Created by Osaka on 18.02.2017.
 */
public class PlacesDbHelper extends SQLiteOpenHelper {

    public static class PlaceEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "_id";
        public static final String TABLE_NAME = "place";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
    }


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Places3.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceEntry.TABLE_NAME + " (" +
                    PlaceEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY autoincrement," +
                    PlaceEntry.COLUMN_NAME_NAME + " TEXT," +
                    PlaceEntry.COLUMN_NAME_DESCRIPTION + " TEXT ," +
                    PlaceEntry.COLUMN_NAME_LAT + " FLOAT , " +
                    PlaceEntry.COLUMN_NAME_LNG + " FLOAT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceEntry.TABLE_NAME;

    public PlacesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}






