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
    private final int ModifyRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(CLASS_NAME, "onCreate");

        memoActivityOperate_Button = (Button) findViewById(R.id.memoActivityOperate_Button);
        memoActivityOperate_Button.setOnClickListener(this::memoActivityOperate_ButtonClick);

        dbHelper = DBHelper.getInstance(this);
        dbHelper.createTable();
        dbHelper.selectDataTableAllIndex();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setNotifyWhenUseRecyclerView();
    }

    private void memoActivityOperate_ButtonClick(View view) {
        Intent intent = new Intent(this, RegisterNotepadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setNotifyWhenUseRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASS_NAME, "onResume");
        setNotifyWhenUseRecyclerView();
    }

    private void setNotifyWhenUseRecyclerView() {
        adapter = new RecyclerViewAdapter(this, dbHelper.getDataTableIndex());//dbHelper.getDataTableIndex 테이블 메모 데이터들
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void itemOnClick(String textViewStr) {
                Log.i("textviewStr", textViewStr);
                showDetailedNotepad(textViewStr);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void itemLongClick(String textViewStr) {
                dbHelper.deleteDataTableIndex(textViewStr);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//View를 언제 재사용하는지를 결정
        recyclerView.setAdapter(adapter);
    }//end setNotifyWhenUseRecyclerView

    private void showDetailedNotepad(String selectedData){
        Intent intent = new Intent(this, DetailedNotepadActivity.class);
        final String DATA_KEY = "DATA_KEY";
        intent.putExtra(DATA_KEY, selectedData);
        startActivityForResult(intent, ModifyRequest);
    }//end showDetailedNotepad



}
