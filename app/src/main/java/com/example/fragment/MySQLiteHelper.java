package com.example.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "---";

    public MySQLiteHelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Message (_id varchar(20) primary key,password varchar(20),style " +
                "varchar(100),sex varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "old version:" + oldVersion + "new version:" + newVersion);
    }
}
