package com.example.user.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class NotepadActivity extends Activity {
    private Button noteRegister_Button;
    private EditText note_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        noteRegister_Button = (Button)findViewById(R.id.noteRegister_Button);
        note_EditText = (EditText)findViewById(R.id.note_EditText);
    }
}
