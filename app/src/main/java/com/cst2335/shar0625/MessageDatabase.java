package com.cst2335.shar0625;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MessageDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ChatDB";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Chats";
    public static final String COL_ID = "_id";
    public static final String COL_CHAT = "CHAT";
    public static final String COL_CHAT_TYPE = "CHAT_TYPE";

    public MessageDatabase(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable= ("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CHAT + " TEXT, " + COL_CHAT_TYPE + " INTEGER)");
        sqLiteDatabase.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i("Database Upgrade", "Old Version:" + oldVersion + " newVersion:" + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public int deleteEntry(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String where="MessageID=?";
        int numberOFEntriesDeleted= db.delete(TABLE_NAME, where, new String[]{Integer.toString(id)});
        return numberOFEntriesDeleted;
    }


}