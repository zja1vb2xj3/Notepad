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

    static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
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

    //삽입된 데이터 반환
    Vector<String> getDataTableIndex() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        Vector<String> datas = new Vector<>();
        while (cursor.moveToNext()) {
            result = cursor.getString(1) + "\n";
            datas.add(result);
        }

        return datas;
    }
    //데이터 삽입
    boolean insertDataTableIndex(String data) {
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("data", data);
            db.insert(TABLE_NAME, null, values);

            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;
    }


    void inquiryTable() {
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name " +
                        "FROM sqlite_master " +
                        "WHERE type = 'table'", null);
        String result = "";
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + "\n";
        }
    }


    void dropTable() {
        db = getWritableDatabase();
        db.rawQuery("DROP TABLE " + TABLE_NAME, new String[]{});
    }

    void createTable() {
        db = getWritableDatabase();
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " Data TEXT);";
        db.execSQL(query);
    }

    int getTableIdCount() {
        int tableSize = selectDataTableAllIndex();

        return tableSize;
    }


    void deleteDataTableIndex(String data) {
        System.out.println(data);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME +
                " WHERE Data = '" + data + "'; ";
        db.execSQL(sql);
        db.close();

        Log.i(CLASS_NAME, "delete Sucess");
    }

    void selectDataTableIndex() {
        db = getReadableDatabase();
        String sql = "Select * from " + TABLE_NAME + " Where Data";
        Cursor cursor = db.query(TABLE_NAME, null, null, new String[]{"Data"},null,null,null,null);
        String result = "";
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result += cursor.getString(0) + "\n";
            }
            Log.i("DataTable.Data", result);
        }
    }

    //    String getPrimaryKey(){
//        db = getWritableDatabase();
//        String query = "SELECT * FROM "+ TABLE_NAME + " Where = id ";
//        Cursor cursor = db.rawQuery(query, null);
//        String result = "";
//        Vector<Vector> primaryKeys = new Vector<>();
//    }
//
    void updateDataTableItem(String data, int position){
        db = getWritableDatabase();

        String sql =
                        " Update "+ TABLE_NAME +
                        " Set Data = " + " '"+data+"' "+
                        " Where id = " + position;
        db.execSQL(sql);
    }

    int selectId(String data) {
        db = getReadableDatabase();

        String replace = data.replace(System.getProperty("line.separator"),"");

        String sql = "Select id From " + TABLE_NAME + " Where Data Like '"+ replace +"';";

        Cursor cursor = db.rawQuery(sql, null);
        String strId = "";
        while (cursor.moveToNext()) {
            strId = cursor.getString(0);
        }

        int id = Integer.parseInt(strId);
//        System.out.println(id);
        db.close();

        return id;
    }

    int selectDataTableAllIndex() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        int size = 0;
        while (cursor.moveToNext()) {
            result += cursor.getString(0) +
                    cursor.getString(1) + "\n";
            size++;
        }
        Log.i("selectDataTable", result);
        Log.i(CLASS_NAME, "select Sucess");

        return size;
    }

}
