package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailedNotepadActivity extends Activity {
    private Button checkCompletion_Button;
    private EditText detailed_EditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notepad);
        checkCompletion_Button = (Button)findViewById(R.id.checkCompletion_Button);
        checkCompletion_Button.setOnClickListener(this::checkCompletion_ButtonClick);
        detailed_EditText = (EditText)findViewById(R.id.detailed_EditText);
    }

    private void checkCompletion_ButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
