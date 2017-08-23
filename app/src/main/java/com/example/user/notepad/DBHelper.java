package com.example.user.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * DBHelper Singleton pattern 클래스.
 */

public class DBHelper extends SQLiteOpenHelper {
    /**
     * SQLite DB이름
     */
    private static final String DB_NAME = "NotepadDB.db";
    /**
     * SQLite DB버젼
     */
    private static final int DB_VERSION = 1;
    /**
     * SQLite Table이름
     */
    private final String TABLE_NAME = "DataTable";
    /**
     * SQLite DB이름
     */
    private final String CLASS_NAME = "DBHelper";
    /**
     * SQLite 데이터베이스를 관리 및 수행 하는 객체
     */
    private SQLiteDatabase db;

    public DBHelper(Context context) {
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
    public ArrayList<String> getDataTableIndex() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        ArrayList<String> datas = new ArrayList<>();

        while (cursor.moveToNext()) {
            result = cursor.getString(1) + "\n";

            datas.add(result);
        }
        db.close();

        return datas;
    }

    //데이터 삽입
    public boolean insertDataTableIndex(String data) {
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("data", data);
            db.insert(TABLE_NAME, null, values);
            db.close();
            return true;

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;

    }

    //테이블
    public void PrintAllTable() {
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name " +
                        "FROM sqlite_master " +
                        "WHERE type = 'table'", null);

        String result = "";
        result = returnQueryStr(cursor, 0);

        db.close();
        System.out.println(result);
    }


    public void dropTable() {
        db = getWritableDatabase();
        db.rawQuery("DROP TABLE " + TABLE_NAME, new String[]{});
        db.close();
    }

    public void createTable() {
        db = getWritableDatabase();
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " Data TEXT);";
        db.execSQL(query);
        db.close();
    }


    boolean deleteDataTableIndex(String data) {
        if (!data.equals("")) {
            System.out.println(data);
            db = getWritableDatabase();
            String sql = "DELETE FROM " + TABLE_NAME +
                    " WHERE Data = '" + data + "'; ";
            db.execSQL(sql);
            db.close();

            Log.i(CLASS_NAME, "delete Sucess");

            return true;
        } else
            return false;
    }

    void updateDataTableItem(String data, int position) {
        db = getWritableDatabase();

        String sql =
                " Update " + TABLE_NAME +
                        " Set Data = " + " '" + data + "' " +
                        " Where id = " + position;
        db.execSQL(sql);
        db.close();
    }

    int selectId(String data) {
        db = getReadableDatabase();

        String replaceStr = data.replace(System.getProperty("line.separator"), "");

        String sql =
                        " Select id From "
                        + TABLE_NAME +
                        " Where Data Like '" +
                        replaceStr + "';";

        Cursor cursor = db.rawQuery(sql, null);
        db.close();

        String idStr = "";
        idStr = returnQueryStr(cursor, 0);
        int id = Integer.parseInt(idStr);

        return id;
    }

    public void selectDataTable() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = returnQueryStr(cursor, 0);
        Log.i("selectDataTable", result);
        db.close();
    }

    public int getQueryCount(Cursor cursor){
        int count = 0;
        while (cursor.moveToNext()){
            cursor.getString(0);
            count++;
        }
        return count;
    }

    public String returnQueryStr(Cursor cursor, int getStrLength) {
        String result = "";

        while (cursor.moveToNext()) {
            switch (getStrLength) {
                case 0:
                    result += cursor.getString(0);
                    break;
                case 1:
                    result += cursor.getString(0) + cursor.getString(1) + "\n";
                    break;

                default:
                    result = null;
                    break;
            }
        }
        return result;
    }

}//end DBHelper.class

//    public int getTableIdCount() {
//        int tableSize = selectDataTableAllIndex();
//
//        return tableSize;
//    }


