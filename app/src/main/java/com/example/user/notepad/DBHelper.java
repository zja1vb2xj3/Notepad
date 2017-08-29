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

    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        readableDatabase = getReadableDatabase();
        writableDatabase = getWritableDatabase();
    }

//    private static DBHelper dbHelper;
//
//    static DBHelper getInstance(Context context) {
//        if (dbHelper == null) {
//            dbHelper = new DBHelper(context);
//        }
//        return dbHelper;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**삽입된 데이터 반환 */
    public ArrayList<String> getDatabasRowDatas() {

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = "";
        ArrayList<String> datas = new ArrayList<>();

        while (cursor.moveToNext()) {
            result = cursor.getString(1) + "\n";

            datas.add(result);
        }

        return datas;
    }

    int getDatabaseRowId(String data) {

        String replaceStr = data.replace(System.getProperty("line.separator"), "");

        String sql =
                " Select id From "
                        + TABLE_NAME +
                        " Where Data Like '" +
                        replaceStr + "';";
        System.out.println("replaceStr"+replaceStr);
        Cursor cursor = readableDatabase.rawQuery(sql, null);

        String idStr = "";
        idStr = returnQueryStr(cursor, 0);
        int id = Integer.parseInt(idStr);

        return id;
    }

    //데이터 삽입
    public boolean insertDataTableIndex(String data) {
        try {
            ContentValues values = new ContentValues();
            values.put("data", data);
            writableDatabase.insert(TABLE_NAME, null, values);
            return true;

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;

    }


    public void dropTable() {
        writableDatabase.execSQL("Drop Table If Exists "+ TABLE_NAME);
    }

    public void createTable() {
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " Data TEXT);";
        writableDatabase.execSQL(query);
    }


    boolean deleteDataTableIndex(String data) {
        if (!data.equals("")) {
            System.out.println(data);

            String sql = "DELETE FROM " + TABLE_NAME +
                    " WHERE Data = '" + data + "'; ";
            writableDatabase.execSQL(sql);

            Log.i(CLASS_NAME, "delete Sucess");

            return true;
        }
        else
            return false;
    }

    void updateDataTableItem(String data, int position) {
        String sql =
                " Update " + TABLE_NAME +
                        " Set Data = " + " '" + data + "' " +
                        " Where id = " + position;
        writableDatabase.execSQL(sql);
    }


    public void selectDataTable() {
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        String result = returnQueryStr(cursor, 1);
        Log.i("selectDataTable", result);
    }

    //테이블 출력
    public void printAllTable() {
        Cursor cursor = writableDatabase.rawQuery(
                        "SELECT name " +
                        "FROM sqlite_master " +
                        "WHERE type = 'table'", null
        );

        String result = "";
        result = returnQueryStr(cursor, 0);

        System.out.println(result);
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
            }//end switch

        }//end while

        return result;
    }

}//end DBHelper.class



