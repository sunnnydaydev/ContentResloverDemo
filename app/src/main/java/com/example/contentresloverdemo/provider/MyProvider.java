package com.example.contentresloverdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.contentresloverdemo.dao.DbOpenHelper;

/**
 * Created by sunnyDay on 2019/6/28 11:39
 */
public class MyProvider extends ContentProvider {

    private static final String authority = "com.example.contentresloverdemo.provider"; //authority strings from manifest file

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + authority + "book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + authority + "user");

    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    // uri 和code 建立联系 。建立联系后我们可以根据uri的到code值，根据code值 的到数据表名称
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); //uriMatcher 对象 参数为UriMatcher 内部提供的常量

    static {
        uriMatcher.addURI(authority, "book", BOOK_URI_CODE);// 代表 BOOK_CONTENT_URI
        uriMatcher.addURI(authority, "user", USER_URI_CODE); // 代表 USER_CONTENT_URI
    }

    private SQLiteDatabase mDB;

    /**
     * 根据 uri 获得表名
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) { //int match(uri)
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        mDB = new DbOpenHelper(getContext()).getReadableDatabase();

        mDB.execSQL("insert into book values (3,'android')");
        mDB.execSQL("insert into book values (4,'java')");

        mDB.execSQL("insert into user values (1,'tom',20)");
        mDB.execSQL("insert into user values (2,'kate',20)");
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String tableName = getTableName(uri);//获得用户想要访问的表名
        if (tableName == null) {
            throw new ArithmeticException("un support uri:" + uri);
        }
        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName(uri);//获得用户想要访问的表名
        if (tableName == null) {
            throw new ArithmeticException("un support uri:" + uri);
        }
        mDB.insert(tableName,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);//获得用户想要访问的表名
        if (tableName == null) {
            throw new ArithmeticException("un support uri:" + uri);
        }
        int count = mDB.delete(tableName,selection,selectionArgs);
        if (count>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);//获得用户想要访问的表名
        if (tableName == null) {
            throw new ArithmeticException("un support uri:" + uri);
        }
        int row = mDB.update(tableName,values,selection,selectionArgs);
        if (row>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return row;
    }
}
