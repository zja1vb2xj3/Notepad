package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNotepadActivity extends Activity {
    private Button noteRegister_Button;
    private EditText note_EditText;
    private DBHelper dbHelper;
    private final String CLASS_NAME = "NotepadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        note_EditText = (EditText) findViewById(R.id.note_EditText);
        noteRegister_Button = (Button) findViewById(R.id.noteRegister_Button);
        noteRegister_Button.setOnClickListener(this::noteRegister_ButtonClick);

        dbHelper = DBHelper.getInstance(this);
    }

    private void noteRegister_ButtonClick(View view) {
        String memoData = String.valueOf(note_EditText.getText());
        Log.i(CLASS_NAME, memoData);
        //Pacelable 로 Main에 보내면서 MainActivity실행 후 RecyclerView에 데이터 추가됨

        boolean insertSign = dbHelper.insertDataTableIndex(memoData);
        if (insertSign == true){
            Toast.makeText(getApplicationContext(), "메모등록성공", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "메모등록실패", Toast.LENGTH_LONG).show();
    }
}
