package com.example.user.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

/**
 * Created by user on 2017-08-08.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "NotepadDB.db";
    private static final int DB_VERSION = 1;
    private final String TABLE_NAME = "DataTable";
    private final String CLASS_NAME = "DBHelper";

    private SQLiteDatabase db;

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static DBHelper dbHelper;

    static DBHelper getInstance(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void inquiryTable(){
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                        "SELECT name " +
                        "FROM sqlite_master "+
                        "WHERE type = 'table'", null);
        String result = "";
        while (cursor.moveToNext()){
            result += cursor.getString(0) + "\n";
        }
    }


    void dropTable(){
        db = getWritableDatabase();
        db.rawQuery("DROP TABLE " + TABLE_NAME, new String[]{});
    }

    void createTable(){
        db = getWritableDatabase();
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " Data TEXT);";
        db.execSQL(query);
    }

    int getTableIdCount(){
        int tableSize = selectDataTableAllIndex();

        return tableSize;
    }


    boolean insertDataTableIndex(String data){
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("data", data);
            db.insert(TABLE_NAME, null, values);

            return true;
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
        return false;
    }

    void deleteDataTableIndex(String data){
        System.out.println(data);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME +
                     " WHERE Data = '" + data + "'; ";
        db.execSQL(sql);
        db.close();

        Log.i(CLASS_NAME, "delete Sucess");
    }
    void selectDataTableIndex(){
        db = getReadableDatabase();
        String sql = "Select * from " + TABLE_NAME + " Where Data";
        Cursor cursor = db.rawQuery(sql, null);
        String result = "";
        if(cursor != null) {
            while (cursor.moveToNext()) {
                result += cursor.getString(0) + "\n";
            }
            Log.i("DataTable.Data", result);
        }
    }

    int selectDataTableAllIndex(){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        int size = 0;
        while (cursor.moveToNext()){
                    result += cursor.getString(0) +
                    cursor.getString(1) + "\n";
            size++;
        }
        Log.i("selectDataTable", result);
        Log.i(CLASS_NAME, "select Sucess");

        return size;
    }

    //DB select 데이터 분리
    Vector<String> getDataTableIndex(){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        Vector<String> datas = new Vector<>();
        while (cursor.moveToNext()){
            result = cursor.getString(1) + "\n";
            datas.add(result);
        }

        Log.i("datas", String.valueOf(datas));
        return datas;
    }

//    String getPrimaryKey(){
//        db = getWritableDatabase();
//        String query = "SELECT * FROM "+ TABLE_NAME + " Where = id ";
//        Cursor cursor = db.rawQuery(query, null);
//        String result = "";
//        Vector<Vector> primaryKeys = new Vector<>();
//    }

    void updateDataTableItem(String data, int position){
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Data", data);
        db.update(TABLE_NAME, values, "_id=?", new String[]{String.valueOf(position)});//table, values, whereClause, whereArgs

    }

}
