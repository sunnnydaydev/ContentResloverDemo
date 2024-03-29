package com.example.contentresloverdemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sunnyDay on 2019/6/28 12:00
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider_db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VERSION = 1;

    // book table
    private static final String CREATE_BOOK_TABLE = "create table if not exists " + BOOK_TABLE_NAME
            + "(id integer primary key,name text)";
    // user table
    private static final String CREATE_USER_TABLE = "create table if not exists " + USER_TABLE_NAME
            + "(id integer primary key,name text,sex integer)";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(CREATE_USER_TABLE);
         db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             // TODO nothing
            //  call this method when db version to upgrade
    }
}
