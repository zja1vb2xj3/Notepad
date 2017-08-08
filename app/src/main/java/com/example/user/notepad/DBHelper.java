package com.example.user.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2017-08-08.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "NotepadDB.db";
    private static final int DB_VERSION = 1;
    private final String TABLE_NAME = "DataTable";
    private final String CLASS_NAME = "DBHelper";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotePadTable =
                        "CREATE TABLE " + TABLE_NAME +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " Data TEXT);";
        db.execSQL(createNotePadTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void dropTable(){
        db = getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }

    void insertDataTableIndex(String data){
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data",data);
        db.insert(TABLE_NAME, null, values);

        Log.i(CLASS_NAME, "Insert Sucess");
    }

    void deleteDataTableIndex(String rowNumber){
        db = getWritableDatabase();
        db.delete(TABLE_NAME, "_id = ?",  new String[]{rowNumber});

        Log.i(CLASS_NAME, "delete Sucess");
    }

    String selectDataTableIndex(){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        while (cursor.moveToNext()){
            result += cursor.getString(0) + ") "
                    + cursor.getString(1) + "\n";
        }
        Log.i(CLASS_NAME, "select Sucess");
        return result;
    }
}
