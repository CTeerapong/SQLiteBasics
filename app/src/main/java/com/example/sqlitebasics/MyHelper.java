package com.example.sqlitebasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 13/9/2558.
 */
public class MyHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private  static  final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "contacts";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE_NAMBER = "phone_number";

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTable = "CREATE TABLE %s("+
                "%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "%s TEXT,"+
                "%s TEXT)";

        sqlCreateTable = String.format(sqlCreateTable,TABLE_NAME,COL_ID,COL_NAME,COL_PHONE_NAMBER);

        db.execSQL(sqlCreateTable);

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,"ธีรพงษ์");
        cv.put(COL_PHONE_NAMBER,"00000000000");
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_NAME,"Teerapong");
        cv.put(COL_PHONE_NAMBER,"1111111111");
        db.insert(TABLE_NAME, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
