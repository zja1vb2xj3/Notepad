package com.example.user.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }
}
