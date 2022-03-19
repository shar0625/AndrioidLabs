package com.cst2335.shar0625;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseMessages extends SQLiteOpenHelper {
    private static final String DB_NAME = "Msg_DB";
    private static final String DB_TABLE = "Msg_Table";

    private static final String COL_MSG = "Message";
    private static final String COL_ISSEND = "IsSend";
    private static final String COL_MESSAGEID = "MessageID";
    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE +" ("+COL_MESSAGEID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+COL_MSG + "TEXT, " +COL_ISSEND+" BIT);";
    private static final int DATABASE_VERSION = 1;

    public DatabaseMessages(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.w(DatabaseMessages.class.getName(), "Upgrading database");
    db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
    onCreate(db);
    }

    public void insertData(String message, boolean isSend){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MSG,message);
        if (isSend==true){
            contentValues.put(COL_ISSEND,0);
        }
        else {
            contentValues.put(COL_ISSEND,1);
        }
        db.insert(DB_TABLE,null,contentValues);

    }
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }
}
