package com.example.user.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private DBHelper dbHelper;
    private final String CLASS_NAME = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        dbHelper = new DBHelper(this);
//        dbHelper.selectDataTableIndex();
////        dbHelper.insertDataTableIndex("ㅎㅇ");
////        String result = dbHelper.selectDataTableIndex();
////        Log.i(CLASS_NAME, result);
////
////        dbHelper.deleteDataTableIndex("1");
////        result = dbHelper.selectDataTableIndex();
////        Log.i(CLASS_NAME, result);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this);
    }
}
