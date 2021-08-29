package com.thundercandy.epq.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thundercandy.epq.database.CategoryContract.CardEntry;
import com.thundercandy.epq.database.CategoryContract.CategoryEntry;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbVoiceCards.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " ("
                + CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CategoryEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + CategoryEntry.COLUMN_DATE_CREATED + " INT NOT NULL);";

        String SQL_CREATE_CARD_TABLE = "CREATE TABLE " + CardEntry.TABLE_NAME + " ("
                + CardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CardEntry.CATEGORY_ID + " INTEGER NOT NULL,"
                + CardEntry.COLUMN_TERM + " TEXT NOT NULL,"
                + CardEntry.COLUMN_DEFINITION + " TEXT NOT NULL,"
                + CardEntry.COLUMN_DATE_CREATED + " INT NOT NULL);";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Research what to put in here
    }
}
