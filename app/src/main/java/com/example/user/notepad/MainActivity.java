package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private DBHelper dbHelper;
    private final String CLASS_NAME = "MainActivity";
    private Button memoActivityOperate_Button;
    private Vector<String> tableIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoActivityOperate_Button = (Button) findViewById(R.id.memoActivityOperate_Button);
        memoActivityOperate_Button.setOnClickListener(this::memoActivityOperate_ButtonClick);

        dbHelper = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setNotifyWhenUseRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifyWhenUseRecyclerView();
    }

    private void setNotifyWhenUseRecyclerView() {
        tableIndex = dbHelper.getDataTableIndex();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//View를 언제 재사용하는지를 결정
        adapter = new RecyclerViewAdapter(this, tableIndex);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemView(String itemData, int position) {
                showDetailedNotepad(itemData, position);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onRemove(String itemData, int position) {
                removeNotepadItem(itemData, position);
                dbHelper.deleteDataTableIndex(String.valueOf(position+1));
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void removeNotepadItem(String itemData, int position) {
            adapter.removeItemIndex(position);
    }

    private void showDetailedNotepad(String selectedData, int position){
        Intent intent = new Intent(this, DetailedNotepadActivity.class);
        final String DATA_KEY = "DATAKEY";
        final String POSITION_KEY = "POSITIONKEY";

        intent.putExtra(DATA_KEY, selectedData);
        intent.putExtra(POSITION_KEY, position);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setNotifyWhenUseRecyclerView();
    }

    private void memoActivityOperate_ButtonClick(View view) {
        Intent intent = new Intent(this, RegisterNotepadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
