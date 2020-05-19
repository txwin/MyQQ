package com.example.fragment;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;
    private static UriMatcher uriMatcher;
    private Cursor cursor;
    private int tag;

    //初始化UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.fragment", "Message", 1);     //设置UriMatcher的匹配参数
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        if (uriMatcher.match(uri) == 1) {
            tag = database.delete("Message", selection, selectionArgs);
            if (tag != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return tag;
        } else {
            return 0;
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (uriMatcher.match(uri) == 1) {
            if (database.insert("Message", null, values) != -1) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        //初始化数据库
        mySQLiteHelper = new MySQLiteHelper(getContext(), "data.db", null, 1);
        database = mySQLiteHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        if (uriMatcher.match(uri) == 1) {
            cursor = database.query("Message", null, selection, selectionArgs, null, null, null);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        if (uriMatcher.match(uri) == 1) {
            tag = database.update("Message", values, selection, selectionArgs);
            if (tag != -1) {
                getContext().getContentResolver().notifyChange(uri, null);
                return tag;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
