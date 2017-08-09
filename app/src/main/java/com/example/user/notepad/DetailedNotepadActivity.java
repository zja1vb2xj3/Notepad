package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailedNotepadActivity extends Activity {
    private Button checkCompletion_Button;
    private EditText detailed_EditText;
    private final String CLASSNAME = "DetailedNotepadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notepad);
        checkCompletion_Button = (Button)findViewById(R.id.checkCompletion_Button);
        checkCompletion_Button.setOnClickListener(this::checkCompletion_ButtonClick);
        detailed_EditText = (EditText)findViewById(R.id.detailed_EditText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            final String DATA_KEY = "DATAKEY";
            final String POSITION_KEY = "POSITIONKEY";
            String data = intent.getExtras().getString(DATA_KEY);
            int position = intent.getExtras().getInt(POSITION_KEY);
            Log.i("String", data);
            Log.i("Int", String.valueOf(position));
        }
    }

    private void checkCompletion_ButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
