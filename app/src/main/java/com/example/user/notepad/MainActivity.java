package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private DBHelper dbHelper;
    private final String CLASS_NAME = "MainActivity";
    private Button memoActivityOperate_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoActivityOperate_Button = (Button)findViewById(R.id.memoActivityOperate_Button);
        memoActivityOperate_Button.setOnClickListener(this::memoActivityOperate_ButtonClick);

//        dbHelper = new DBHelper(this);
//        dbHelper.selectDataTableIndex();
//        dbHelper.insertDataTableIndex("ㅎㅇ");
//        String result = dbHelper.selectDataTableIndex();
//        Log.i(CLASS_NAME, result);
//
//        dbHelper.deleteDataTableIndex("1");
//        result = dbHelper.selectDataTableIndex();
//        Log.i(CLASS_NAME, result);
//
//        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RecyclerViewAdapter(this);
    }

    private void memoActivityOperate_ButtonClick(View view) {
        Intent intent = new Intent(this, NotepadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
